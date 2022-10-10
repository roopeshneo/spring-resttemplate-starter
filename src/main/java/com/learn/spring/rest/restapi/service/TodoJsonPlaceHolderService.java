package com.learn.spring.rest.restapi.service;

import com.learn.spring.rest.restapi.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TodoJsonPlaceHolderService implements ITodoJsonPlaceHolderService {


    @Autowired
    public RestTemplate restTemplateWithErrorHandler;


    @Override
    @Cacheable("todos")
    public List<Todo> findAll() {
        return Objects.requireNonNull(restTemplateWithErrorHandler.getForObject(TODO_API_URL, List.class));

    }

    @Override
    @Cacheable("todo")
    public Todo findById(Long id) {
        return Objects.requireNonNull(restTemplateWithErrorHandler.getForObject(TODO_API_URL + "/" + id, Todo.class));
    }
}
