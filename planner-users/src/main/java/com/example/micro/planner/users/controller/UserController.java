package com.example.micro.planner.users.controller;

import com.example.micro.planner.entity.User;
import com.example.micro.planner.users.search.UserSearchValues;
import com.example.micro.planner.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String ID_COLUMN = "id";

    private UserService userService;

    @PostMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(String.format("User with id = %d not found", id), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/email")
    public ResponseEntity<?> findByEmail(@RequestBody String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(String.format("User with email = %s not found", email), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody User user) {
        if (user.getId() != null && user.getId() != 0) {
            return new ResponseEntity<>("redundant field id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return new ResponseEntity<>("field username MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return new ResponseEntity<>("field email MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return new ResponseEntity<>("field password MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(userService.add(user));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody User user) {
        if (user.getId() == null || user.getId() == 0) {
            return new ResponseEntity<>("redundant field id MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return new ResponseEntity<>("field username MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return new ResponseEntity<>("field email MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return new ResponseEntity<>("field password MUST NOT be null", HttpStatus.NOT_ACCEPTABLE);
        }

        userService.update(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> deleteByEmail(@RequestBody String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> findByParams(@RequestBody UserSearchValues userSearchValues) {
        String username = userSearchValues.getUsername();
        String email = userSearchValues.getEmail();

        String direction = userSearchValues.getDirection();
        Sort.Direction sortDirection = direction != null && direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = Optional.ofNullable(userSearchValues.getSortField()).orElse(ID_COLUMN);
        Sort sort = Sort.by(sortDirection, sortField);

        Integer pageNumber = userSearchValues.getPageNumber();
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        Integer pageSize = userSearchValues.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return ResponseEntity.ok(userService.findByParams(username, email, pageRequest));
    }
}
