package com.example.micro.planner.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.micro.planner.entity")
@ComponentScan(basePackages = "com.example.micro.planner")
@EnableJpaRepositories(basePackages = "com.example.micro.planner.todo.repo")
@EnableFeignClients
public class PlannerTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerTodoApplication.class, args);
	}

}
