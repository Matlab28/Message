package com.example.message.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequestDto {
    private String sender;
    private String recipient;
    private String content;
    private String fileName;
    private String image;
    private String description;
}
