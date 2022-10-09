package com.learn.spring.rest.restapi.controller;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Slf4j
public class InitialRestController {


    @Autowired
    TodoJsonPlaceHolderService todoJsonPlaceHolderService;

    @GetMapping("/init")
    public String sayHello() {
        log.info("Received call on /init");

        List<Todo> todoResp = todoJsonPlaceHolderService.findAll();

        Todo todoById = todoJsonPlaceHolderService.findById(1L);

        log.info("Received response from  todo-service: {}", todoResp);
        return new String("Hello World: " + todoById); //TODO
    }

}
