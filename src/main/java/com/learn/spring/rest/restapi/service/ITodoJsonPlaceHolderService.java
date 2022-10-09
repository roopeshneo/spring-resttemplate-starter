package com.learn.spring.rest.restapi.service;

import com.learn.spring.rest.restapi.model.Todo;

import java.util.List;

public interface ITodoJsonPlaceHolderService {

    String TODO_API_URL = "https://jsonplaceholder.typicode.com/todoss";

    List<Todo> findAll();
    Todo findById(Long id);
}
