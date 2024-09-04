package com.example.message.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupResponseDto {
    private Long id;
    private List<String> groupMembers;
    private String groupName;
    private String userName;
    private String number;
    private String description;
}
