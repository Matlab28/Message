package com.example.message.service;

import com.example.message.dto.request.GroupRequestDto;
import com.example.message.dto.response.GroupResponseDto;
import com.example.message.entity.GroupEntity;
import com.example.message.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final ModelMapper modelMapper;
    private final GroupRepository repository;

    public String create(GroupRequestDto dto) {
        GroupEntity entity = modelMapper.map(dto, GroupEntity.class);

        if (repository.existsByNumber(dto.getNumber())) {
            log.error("User entered exist number.");
            return "This number is already defined.";
        }

        List<String> groupMembers = entity.getGroupMembers();

        if (groupMembers == null) {
            dto.setGroupMembers(new ArrayList<>());
        }

        assert groupMembers != null;
        groupMembers.add(dto.getNumber());
        groupMembers.add(dto.getUserName());

        repository.save(entity);
        log.info("Group info saved in DB.");
        return "Group created successfully!";
    }

    public GroupResponseDto readByUserName(String userName) {
        GroupEntity group = repository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Group member not found by user name - " + userName));

        log.info("\"{}\" user name group member responded.", userName);
        return modelMapper.map(group, GroupResponseDto.class);
    }

    public GroupResponseDto readByNumber(String number) {
        GroupEntity group = repository.findByNumber(number)
                .orElseThrow(() -> new RuntimeException("Group member not found by number - " + number));

        log.info("\"{}\" number group member responded.", number);
        return modelMapper.map(group, GroupResponseDto.class);
    }

    public List<GroupResponseDto> readAll() {
        log.info("All group info responded.");
        return repository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, GroupResponseDto.class))
                .collect(Collectors.toList());
    }

    public GroupResponseDto readById(Long id) {
        GroupEntity group = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found by ID - " + id));

        log.info("\"{}\" ID group info responded.", id);
        return modelMapper.map(group, GroupResponseDto.class);
    }

    public String update(Long id, GroupRequestDto dto) {
        GroupEntity group = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found by ID - " + id));

        modelMapper.map(dto, group);
        repository.save(group);
        log.info("\"{}\" ID group info updated.", id);
        return "Group updated successfully!";
    }

    public String delete(Long id) {
        GroupEntity group = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found by ID - " + id));

        repository.delete(group);
        log.info("\"{}\" ID group info deleted.", id);
        return "Group deleted successfully!";
    }
}
