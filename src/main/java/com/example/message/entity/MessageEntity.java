package com.example.message.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message_info")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Sender cannot be null.")
    @Size(min = 1, message = "Sender cannot be empty.")
    @Column(nullable = false)
    private String sender;

    @NotNull(message = "Recipient cannot be null.")
    @Size(min = 1, message = "Recipient cannot be empty.")
    @Column(nullable = false)
    private String recipient;

    @NotNull(message = "Content cannot be null.")
    @Size(min = 1, message = "Content cannot be empty.")
    @Column(nullable = false)
    private String content;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(length = 200)
    private String description;
}
