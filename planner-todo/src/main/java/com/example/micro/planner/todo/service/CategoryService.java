package com.example.micro.planner.todo.service;

import com.example.micro.planner.entity.Category;
import com.example.micro.planner.todo.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Category> findByUserId(Long userId) {
        return categoryRepository.findByUserIdOrderByTitleAsc(userId);
    }

    @Transactional(readOnly = true)
    public List<Category> findByUserIdAndTitlePattern(Long userId, String title) {
        return categoryRepository.findByUserIdAndTitlePattern(userId, title);
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
