package com.learn.spring.rest.restapi.controller;

import static jdk.internal.net.http.common.Log.headers;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InitRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
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

    mockMvc.perform(get("https://jsonplaceholder.typicode.com/todos/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(header().string("version", "v1"))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.userId", is(1)))
        .andExpect(jsonPath("$.title", is("delectus aut autem")))
        .andExpect(jsonPath("$.completed", is(false)));

    verify(mockRepository, times(1)).findById(1L);

  }

  @Test
  public void find_TodoIdNotFound_404() throws Exception {
    mockMvc.perform(get("https://jsonplaceholder.typicode.com/todos/0")).andExpect(status().isNotFound());
  }

  @Test
  public void find_allTodos_OK() throws Exception {

    List<Todo> todos = Arrays.asList(
        new Todo(1, 1, "delectus aut autem", false),
        new Todo(1, 2, "quis ut nam facilis et officia qui", false));

    when(mockRepository.findAll()).thenReturn(todos);

    mockMvc.perform(get("https://jsonplaceholder.typicode.com/todos"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].userId", is(1)))
        .andExpect(jsonPath("$[0].title", is("delectus aut autem")))
        .andExpect(jsonPath("$[0].completed", is(false)))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].userId", is(1)))
        .andExpect(jsonPath("$[1].title", is("quis ut nam facilis et officia qui")))
        .andExpect(jsonPath("$[1].completed", is(false)))
        ;

    verify(mockRepository, times(1)).findAll();
  }
}
