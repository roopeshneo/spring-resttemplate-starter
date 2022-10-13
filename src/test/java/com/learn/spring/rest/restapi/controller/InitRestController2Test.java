package com.learn.spring.rest.restapi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class InitRestController2Test {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private TestRestTemplate restTemplate;


  @MockBean
  private TodoJsonPlaceHolderService mockRepository;

  /*
  {
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
   */
  @Before
  public void init() {
    Todo todo = new Todo(1, 1, "delectus aut autem", false);
    when(mockRepository.findById(1L)).thenReturn(todo);
  }

  @Test
  public void find_Todo_byId_OK() throws Exception {
    String expectedTodo = "{id:1,userId:1,title:\"delectus aut autem\",completed:false}";
//    Todo expectedTodo = new Todo(1, 1, "delectus aut autem", false);
    ResponseEntity<String> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos/1", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, response.getHeaders().getContentType());
    JSONAssert.assertEquals(expectedTodo, response.getBody(), false);
    verify(mockRepository, times(1)).findById(1L);
  }
}
