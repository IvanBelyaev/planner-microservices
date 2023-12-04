package com.example.micro.planner.utils.resttemplate;

import com.example.micro.planner.entity.User;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Log
@Component
public class UserRestBuilder {
    private static final String BASE_URL = "http://localhost:8765/planner-users/user/";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean exists(Long id) {
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    BASE_URL + id, HttpMethod.POST, HttpEntity.EMPTY, User.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}

