package com.example.micro.planner.todo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("planner-users")
public interface UserFeignClient {

    @PostMapping("/user/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id);
}
