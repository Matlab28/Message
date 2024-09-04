package com.example.message.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_info")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @Column(name = "group_members")
    private List<String> groupMembers;

    @NotNull(message = "Group name cannot be null.")
    @Min(value = 1, message = "Group name cannot be empty.")
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @NotNull(message = "User name cannot be null.")
    @Min(value = 1, message = "User name cannot be empty.")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull(message = "Number cannot be null")
    @Min(value = 1, message = "Number cannot be empty")
    private String number;

    private String description;
}
