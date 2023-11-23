package com.example.micro.planner.todo.repo;

import com.example.micro.planner.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Query("select p from Priority p where p.userId = :userId " +
            "and (:title is null or :title = '' or lower(p.title) like lower(concat('%', :title, '%')))")
    List<Priority> findByUserIdAndTitlePattern(@Param("userId") Long userId, @Param("title") String titlePattern);
}
