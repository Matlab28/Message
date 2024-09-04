package com.example.message.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupRequestDto {
    private List<String> groupMembers;
    private String groupName;
    private String userName;
    private String number;
    private String description;
}
