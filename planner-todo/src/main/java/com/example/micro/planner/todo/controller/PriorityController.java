package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Priority;
import com.example.micro.planner.todo.search.PrioritySearchValues;
import com.example.micro.planner.todo.service.PriorityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priority")
@AllArgsConstructor
public class PriorityController {
    private PriorityService priorityService;

    @PostMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Priority priority = priorityService.findById(id);
        if (priority == null) {
            return new ResponseEntity<>(String.format("Priority with id = %d not found", id), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(priority);
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity<>("redundant field id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().isBlank()) {
            return new ResponseEntity<>("field color MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getUserId() == null || priority.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Priority priority) {
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity<>("field id MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().isBlank()) {
            return new ResponseEntity<>("field color MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getUserId() == null || priority.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        priorityService.update(priority);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        priorityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchByUserIdAndTitle(@RequestBody PrioritySearchValues searchValues) {
        if (searchValues.getUserId() == null || searchValues.getUserId() == 0) {
            return new ResponseEntity<>("field userId MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        List<Priority> categories =
                priorityService.findByEmailAndTitlePattern(searchValues.getUserId(), searchValues.getTitle());
        return ResponseEntity.ok(categories);
    }
}
