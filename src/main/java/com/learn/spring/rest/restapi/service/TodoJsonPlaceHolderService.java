package com.learn.spring.rest.restapi.service;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.model.response.TodoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TodoJsonPlaceHolderService {

    private static final String TODO_API_URL = "https://jsonplaceholder.typicode.com/todos";

    private final RestTemplate restTemplate;

    public TodoJsonPlaceHolderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Todo> getTodos() {
        return Objects.requireNonNull(restTemplate.getForObject(TODO_API_URL, TodoResponse.class)).getTodos();

    }
}
