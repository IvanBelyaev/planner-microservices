package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Stat;
import com.example.micro.planner.todo.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
@AllArgsConstructor
public class StatController {
    private StatService statService;

    @PostMapping
    public ResponseEntity<?> findByEmail(@RequestBody Long userId) {
        Stat statistics = statService.findByUserId(userId);
        if (statistics == null) {
            return new ResponseEntity<>(String.format("statistics for userId=%s not found", userId), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statistics);
    }
}
