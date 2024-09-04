package com.example.message.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponseDto {
    private Long id;
    private String sender;
    private String recipient;
    private String content;
    private String fileName;
    private String image;
    private String description;
}
