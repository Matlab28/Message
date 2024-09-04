package com.example.message.service;

import com.example.message.dto.request.MessageRequestDto;
import com.example.message.dto.response.MessageResponseDto;
import com.example.message.entity.MessageEntity;
import com.example.message.repository.MessageRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final ModelMapper modelMapper;
    private final MessageRepository repository;
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public void addSession(String username, WebSocketSession session) {
        userSessions.put(username, session);
    }

    public void removeSession(String username) {
        userSessions.remove(username);
    }

    public void sendMessage(MessageRequestDto requestDto) {
        MessageEntity entity = modelMapper.map(requestDto, MessageEntity.class);
        WebSocketSession recipientSession = userSessions.get(requestDto.getRecipient());
        if (recipientSession != null && recipientSession.isOpen()) {
            try {
                MessageResponseDto responseDto = new MessageResponseDto();
                MessageEntity map = modelMapper.map(responseDto, MessageEntity.class);
                byte[] imageBytes = Base64.getDecoder().decode(requestDto.getImage());
                map.setImage(imageBytes);
                entity.setImage(imageBytes);
                responseDto.setSender(requestDto.getSender());
                responseDto.setContent(requestDto.getContent());

                recipientSession.sendMessage(new TextMessage(responseDto.getContent()));
                repository.save(map);
            } catch (IOException e) {
                log.error("Something went wrong while sending message.");
                e.printStackTrace();
            }
        }
        repository.save(entity);
    }

    private byte[] downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to download image");
            }

            InputStream inputStream = connection.getInputStream();
            return StreamUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            log.error("Failed to download image.");
            throw new RuntimeException("Failed to download image");
        }
    }

    public MessageResponseDto convertToResponseDto(MessageEntity entity) {
        return modelMapper.map(entity, MessageResponseDto.class);
    }

    public MessageResponseDto saveMessageWithImage(MessageRequestDto dto) {
        MessageEntity entity = modelMapper.map(dto, MessageEntity.class);

        if (dto.getFileName() != null && dto.getImage() == null) {
            byte[] image = downloadImage(dto.getFileName());
            entity.setImage(image);
            entity.setFileName(dto.getFileName());
        } else {
            entity.setFileName(dto.getFileName());
        }

        return convertToResponseDto(repository.save(entity));
    }

    public MessageResponseDto readByRecipient(String recipient) {
        MessageEntity message = repository.findByRecipient(recipient)
                .orElseThrow(() -> new RuntimeException("Message not found by recipient - " + recipient));

        log.info("\"{}\" recipient's message responded.", recipient);
        return modelMapper.map(message, MessageResponseDto.class);
    }

    public List<MessageResponseDto> readAll() {
        log.info("All messages responded.");
        return repository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, MessageResponseDto.class))
                .collect(Collectors.toList());
    }

    public MessageResponseDto readById(Long id) {
        MessageEntity message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found by ID - " + id));

        log.info("\"{}\" ID message responded.", id);
        return modelMapper.map(message, MessageResponseDto.class);
    }

    public void update(Long id, MessageRequestDto dto) {
        MessageEntity message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found by ID - " + id));

        modelMapper.map(dto, message);
        repository.save(message);
        log.info("\"{}\" ID message updated.", id);
    }

    public void delete(Long id) {
        MessageEntity message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found by ID - " + id));

        repository.delete(message);
        log.info("\"{}\" ID message deleted", id);
    }
}
