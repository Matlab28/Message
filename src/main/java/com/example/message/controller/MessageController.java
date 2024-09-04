package com.example.message.controller;

import com.example.message.dto.request.MessageRequestDto;
import com.example.message.dto.response.MessageResponseDto;
import com.example.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequestDto requestDto) {
        try {
            service.sendMessage(requestDto);
            return ResponseEntity.ok("Message sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send message.");
        }
    }

    @GetMapping("/read-by-recipient")
    public ResponseEntity<MessageResponseDto> readByRecipient(@RequestParam String recipient) {
        try {
            return ResponseEntity.ok(service.readByRecipient(recipient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<MessageResponseDto>> readAll() {
        try {
            return ResponseEntity.ok(service.readAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/save-with-image")
    public ResponseEntity<MessageResponseDto> saveMessageWithImage(@RequestBody MessageRequestDto dto) {
        try {
            return ResponseEntity.ok(service.saveMessageWithImage(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<MessageResponseDto> readById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.readById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody MessageRequestDto dto) {
        try {
            service.update(id, dto);
            return ResponseEntity.ok("Message updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update message.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Message deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete message.");
        }
    }
}
