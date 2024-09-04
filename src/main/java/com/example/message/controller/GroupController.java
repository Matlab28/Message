package com.example.message.controller;

import com.example.message.dto.request.GroupRequestDto;
import com.example.message.dto.response.GroupResponseDto;
import com.example.message.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService service;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody GroupRequestDto dto) {
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong...");
        }
    }

    @GetMapping("/read-by-user-name")
    public ResponseEntity<GroupResponseDto> readByUserName(@RequestParam String userName) {
        try {
            return ResponseEntity.ok(service.readByUserName(userName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-by-number")
    public ResponseEntity<GroupResponseDto> readByNumber(@RequestParam String number) {
        try {
            return ResponseEntity.ok(service.readByNumber(number));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<GroupResponseDto>> readAll() {
        try {
            return ResponseEntity.ok(service.readAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<GroupResponseDto> readById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.readById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody GroupRequestDto dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong...");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong...");
        }
    }
}
