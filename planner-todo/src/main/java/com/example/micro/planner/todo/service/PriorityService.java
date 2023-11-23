package com.example.micro.planner.todo.service;

import com.example.micro.planner.entity.Priority;
import com.example.micro.planner.todo.repo.PriorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PriorityService {
    private PriorityRepository priorityRepository;

    @Transactional(readOnly = true)
    public Priority findById(Long id) {
        return priorityRepository.findById(id).orElse(null);
    }

    public void update(Priority priority) {
        priorityRepository.save(priority);
    }

    public Priority add(Priority priority) {
        return priorityRepository.save(priority);
    }

    public void deleteById(Long id) {
        priorityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Priority> findByEmailAndTitlePattern(Long userId, String titlePattern) {
        return priorityRepository.findByUserIdAndTitlePattern(userId, titlePattern);
    }
}
