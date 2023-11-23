package com.example.micro.planner.todo.repo;

import com.example.micro.planner.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t join fetch t.priority p join fetch t.category c " +
            "where (:title is null or :title = '' or lower(t.title) like lower(concat('%', :title, '%'))) " +
            "and (cast(:dateFrom as timestamp) is null or t.date >= :dateFrom) " +
            "and (cast(:dateTo as timestamp) is null or t.date <= :dateTo) " +
            "and (:completed is null or t.completed = :completed) " +
            "and (:priorityId is null or p.id = :priorityId) " +
            "and (:categoryId is null or c.id = :categoryId) " +
            "and t.userId = :userId")
    Page<Task> findByParams(
            @Param("userId") Long userId,
            @Param("title") String title,
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            @Param("completed") Boolean completed,
            @Param("priorityId") Long priorityId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    List<Task> findByUserId(Long userId);
}
