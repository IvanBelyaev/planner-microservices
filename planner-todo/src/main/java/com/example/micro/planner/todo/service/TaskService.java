package com.example.micro.planner.todo.service;

import com.example.micro.planner.entity.Task;
import com.example.micro.planner.todo.repo.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task add(Task task) {
        return taskRepository.save(task);
    }

    public void update(Task task) {
        taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Task> findByParams(Long userId, String title, Date dateFrom, Date dateTo,
                                   Boolean completed, Long priorityId, Long categoryId, Pageable pageable) {
        return taskRepository.findByParams(userId, title, dateFrom, dateTo, completed, priorityId, categoryId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}
