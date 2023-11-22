package com.example.micro.planner.todo.repo;

import com.example.micro.planner.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserIdOrderByTitleAsc(Long userId);

    @Query("select c from Category c " +
            "where c.userId = :userId " +
            "and (:title is null or :title = '' or lower(c.title) like lower(concat('%', :title, '%'))) " +
            "order by c.title asc")
    List<Category> findByUserIdAndTitlePattern(@Param("userId") Long userId, @Param("title") String title);
}
