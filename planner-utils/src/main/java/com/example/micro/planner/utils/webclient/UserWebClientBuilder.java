package com.example.micro.planner.utils.webclient;

import com.example.micro.planner.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserWebClientBuilder {
    private static final String BASE_USER_URL = "http://localhost:8765/planner-users/user/";
    private static final String BASE_INIT_DATA_URL = "http://localhost:8765/planner-todo/data/";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean exists(Long id) {
        try {
            User user = WebClient.create(BASE_USER_URL)
                    .post()
                    .uri(id.toString())
                    .retrieve()
                    .bodyToFlux(User.class)
                    .blockFirst();

            if (user != null) {
                return true;
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public Mono<User> getUserAsync(Long id) {
        return WebClient.create(BASE_USER_URL)
                .post()
                .uri(id.toString())
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<Boolean> initData(Long userId) {
        return WebClient.create(BASE_INIT_DATA_URL)
                .post()
                .uri("/init")
                .bodyValue(userId)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
