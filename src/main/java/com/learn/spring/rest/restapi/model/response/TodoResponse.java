package com.learn.spring.rest.restapi.model.response;

import com.learn.spring.rest.restapi.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    List<Todo> todos;
}
