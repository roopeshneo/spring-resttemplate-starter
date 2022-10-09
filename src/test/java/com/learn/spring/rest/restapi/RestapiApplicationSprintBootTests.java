package com.learn.spring.rest.restapi;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Ignore
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestConfig.class)
class RestapiApplicationSprintBootTests {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    TodoJsonPlaceHolderService todoJsonPlaceHolderService;

//    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
        Todo mockTodo = new Todo();
        mockTodo.setId(1);
        mockTodo.setTitle("delectus aut autem");
        mockTodo.setCompleted(false);
        mockTodo.setUserId(1);
        Mockito
                .when(restTemplate.getForEntity(
                        "https://jsonplaceholder.typicode.com/todos/1", Todo.class))
                .thenReturn(new ResponseEntity(mockTodo, HttpStatus.OK));

        Todo apiTodo = todoJsonPlaceHolderService.findById(1L);
        Assertions.assertEquals(mockTodo, apiTodo);
    }

}
