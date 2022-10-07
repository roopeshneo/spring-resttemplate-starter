package com.learn.spring.rest.restapi.controller;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/api/hello")
    public String sayHello() {
        log.info("Received call on /api/hello. And will call /api/random-name on the random-name-service!");

        Optional<List<Todo>> todoResp = Optional.ofNullable(todoJsonPlaceHolderService.getTodos()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "TODO not found.")));
//                ("http://localhost:8081/api/random-name", Optional.class);

        log.info("Received response from  todo-service: {}", todoResp);
        return new String("Hello World: " + todoResp.get());
    }
}
