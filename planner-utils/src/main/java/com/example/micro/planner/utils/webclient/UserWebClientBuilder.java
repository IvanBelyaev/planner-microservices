package com.example.micro.planner.utils.webclient;

import com.example.micro.planner.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class UserWebClientBuilder {
    private static final String BASE_URL = "http://localhost:8765/planner-users/user/";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean exists(Long id) {
        try {
            User user = WebClient.create(BASE_URL)
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
}
