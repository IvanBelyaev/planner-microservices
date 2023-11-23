package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Task;
import com.example.micro.planner.todo.search.TaskSearchValues;
import com.example.micro.planner.todo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String ID_COLUMN = "id";
    private TaskService taskService;

    @PostMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return new ResponseEntity<>(String.format("Task with id = %d not found", id), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping("/userId")
    public List<Task> findByUserId(@RequestBody Long userId) {
        return taskService.findByUserId(userId);
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity<>("redundant field id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getUserId() == null || task.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getDate() == null) {
            task.setDate(new Date());
        }

        return ResponseEntity.ok(taskService.add(task));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity<>("field id MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getUserId() == null || task.getUserId() <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return new ResponseEntity<>("field title MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getDate() == null) {
            task.setDate(new Date());
        }

        taskService.update(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> findByParams(@RequestBody TaskSearchValues taskSearchValues) {
        Long userId = taskSearchValues.getUserId();

        if (userId == null || userId <= 0) {
            return new ResponseEntity<>(
                    "field userId MUST NOT be null and MUST be positive", HttpStatus.NOT_ACCEPTABLE
            );
        }

        String title = taskSearchValues.getTitle();
        Date dateFrom = taskSearchValues.getDateFrom();
        if (dateFrom != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(dateFrom);
            calendarFrom.set(Calendar.HOUR, 0);
            calendarFrom.set(Calendar.MINUTE, 0);
            calendarFrom.set(Calendar.SECOND, 0);
            calendarFrom.set(Calendar.MILLISECOND, 0);

            dateFrom = calendarFrom.getTime();
        }

        Date dateTo = taskSearchValues.getDateTo();
        if (dateTo != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(dateTo);
            calendarTo.set(Calendar.HOUR, 23);
            calendarTo.set(Calendar.MINUTE, 59);
            calendarTo.set(Calendar.SECOND, 59);
            calendarTo.set(Calendar.MILLISECOND, 999);

            dateTo = calendarTo.getTime();
        }

        Boolean completed = taskSearchValues.getCompleted() == null ? null : taskSearchValues.getCompleted().equals(true);
        Long priorityId = taskSearchValues.getPriorityId();
        Long categoryId = taskSearchValues.getCategoryId();

        String direction = taskSearchValues.getDirection();
        Sort.Direction sortDirection = direction != null && direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = Optional.ofNullable(taskSearchValues.getSortField()).orElse(ID_COLUMN);
        Sort sort = Sort.by(sortDirection, sortField);

        Integer pageNumber = taskSearchValues.getPageNumber();
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        Integer pageSize = taskSearchValues.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return ResponseEntity.ok(taskService.findByParams(userId, title, dateFrom, dateTo, completed, priorityId, categoryId, pageRequest));
    }
}
