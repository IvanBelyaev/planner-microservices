package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Category;
import com.example.micro.planner.todo.search.CategorySearchValues;
import com.example.micro.planner.todo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(String.format("Category with id = %d not found", id), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping("/userId")
    public List<Category> findByEmailPattern(@RequestBody Long userId) {
        return categoryService.findByUserId(userId);
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Category category) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity<>("redundant field id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getUserId() == null || category.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity<>("field id MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getUserId() == null || category.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        if (category.getTitle() == null || category.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        categoryService.update(category);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestBody CategorySearchValues searchValues) {
        if (searchValues.getUserId() == null || searchValues.getUserId() == 0) {
            return new ResponseEntity<>("field userId MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        List<Category> categories =
                categoryService.findByUserIdAndTitlePattern(searchValues.getUserId(), searchValues.getTitle());
        return ResponseEntity.ok(categories);
    }
}
