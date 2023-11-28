package com.example.micro.planner.users.repo;

import com.example.micro.planner.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    @Query("select u from User u left join fetch u.roles r " +
            "where (:email is null or :email = '' or lower(u.email) like lower(concat('%', :email, '%'))) " +
            "and (:username is null or :username = '' or lower(u.username) like lower(concat('%', :username, '%')))")
    Page<User> findByUsernameOrEmailPattern(
            @Param("username") String username,
            @Param("email") String email,
            Pageable pageable
    );
}
