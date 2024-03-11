package com.example.micro.planner.todo.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class FeignExceptionHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new ErrorDecoder.Default().decode(methodKey, response);
    }

    private String getBody(Response response) {
        try(var inputStream = response.body().asInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("failed to extract body from response", e);
        }
        return "";
    }
}
