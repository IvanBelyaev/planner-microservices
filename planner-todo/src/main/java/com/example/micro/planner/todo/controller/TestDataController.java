package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Category;
import com.example.micro.planner.entity.Priority;
import com.example.micro.planner.entity.Task;
import com.example.micro.planner.todo.service.CategoryService;
import com.example.micro.planner.todo.service.PriorityService;
import com.example.micro.planner.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class TestDataController {

    private final CategoryService categoryService;
    private final PriorityService priorityService;
    private final TaskService taskService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> createTestData(@RequestBody Long userId) {
        var familyCategory = new Category(null, "Семья", null, null, userId);
        var workCategory = new Category(null, "Работа", null, null, userId);

        var criticalCategory = new Priority(null, "Очень важная", "#ff0000", userId);
        var majorCategory = new Priority(null, "Важная", "#0000ff", userId);

        var task1 = new Task(
                null, "Сходить в магазин", false, new Date(), userId, familyCategory, majorCategory);
        var task2 = new Task(
                null, "Доделать задачу", true, new Date(), userId, workCategory, criticalCategory);

        categoryService.add(familyCategory);
        categoryService.add(workCategory);
        priorityService.add(criticalCategory);
        priorityService.add(majorCategory);
        taskService.add(task1);
        taskService.add(task2);

        return ResponseEntity.ok(true);
    }
}
