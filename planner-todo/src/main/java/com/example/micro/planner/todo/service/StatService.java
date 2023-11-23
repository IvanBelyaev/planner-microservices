package com.example.micro.planner.todo.service;

import com.example.micro.planner.entity.Stat;
import com.example.micro.planner.todo.repo.StatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class StatService {
    private StatRepository statRepository;

    public Stat findByUserId(Long userId) {
        return statRepository.findByUserId(userId).orElse(null);
    }
}
