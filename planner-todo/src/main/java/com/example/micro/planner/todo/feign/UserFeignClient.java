package com.example.micro.planner.todo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@FeignClient(value = "planner-users", fallback = FallbackUserFeignClient.class)
public interface UserFeignClient {

    @PostMapping("/user/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id);
}

@Component
class FallbackUserFeignClient implements UserFeignClient {

    @Override
    public ResponseEntity<?> findById(Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User service unavailable");
    }
}
