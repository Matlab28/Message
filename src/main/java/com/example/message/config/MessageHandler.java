package com.example.message.config;


import com.example.message.dto.request.MessageRequestDto;
import com.example.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MessageHandler extends TextWebSocketHandler {
    private final MessageService messageService;

    @Autowired
    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = session.getUri().getQuery();
        messageService.addSession(username, session);
        System.out.println("WebSocket connection established for user: " + username);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(":", 2);
        if (parts.length == 2) {
            String recipient = parts[0];
            String content = parts[1];
            String sender = (String) session.getAttributes().get("username");

            MessageRequestDto requestDto = new MessageRequestDto();
            requestDto.setSender(sender);
            requestDto.setRecipient(recipient);
            requestDto.setContent(content);

            messageService.sendMessage(requestDto);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = (String) session.getAttributes().get("username");
        messageService.removeSession(username);
        System.out.println("WebSocket connection closed for user: " + username);
    }
}
