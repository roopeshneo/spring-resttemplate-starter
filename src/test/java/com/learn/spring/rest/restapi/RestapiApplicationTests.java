package com.learn.spring.rest.restapi;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class RestapiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    TodoJsonPlaceHolderService todoJsonPlaceHolderService;

//    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
        Todo mockTodo = new Todo(1, 1, "delectus aut autem", false);
        Mockito
                .when(restTemplate.getForEntity(
                        "https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenReturn(new ResponseEntity(mockTodo, HttpStatus.OK));

        Todo apiTodo = todoJsonPlaceHolderService.findById(1L);
        Assertions.assertEquals(mockTodo, apiTodo);
    }

}
