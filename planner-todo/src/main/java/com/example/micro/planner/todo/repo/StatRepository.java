package com.example.micro.planner.todo.repo;

import com.example.micro.planner.entity.Stat;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatRepository extends CrudRepository<Stat, Long> {
    Optional<Stat> findByUserId(Long userId);
}
