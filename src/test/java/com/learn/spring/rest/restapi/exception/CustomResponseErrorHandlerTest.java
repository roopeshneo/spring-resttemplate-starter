package com.learn.spring.rest.restapi.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.learn.spring.rest.restapi.config.RestTemplateConfig;
import com.learn.spring.rest.restapi.exception.CustomResponseErrorHandler.CustomException;
import com.learn.spring.rest.restapi.model.Todo;
import com.learn.spring.rest.restapi.service.TodoJsonPlaceHolderService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {RestTemplateConfig.class,
// TodoJsonPlaceHolderService.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomException.class, Todo.class, RestTemplateConfig.class})
@RestClientTest
public class CustomResponseErrorHandlerTest {

  private static final String URL = "https://jsonplaceholder.typicode.com/todos";
  private ResponseActions responseActions;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  TodoJsonPlaceHolderService todoJsonPlaceHolderService;

  //  @BeforeEach
  public void setUp() {
    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    restTemplateBuilder.configure(restTemplate);
    TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    responseActions = MockRestServiceServer.createServer(
            todoJsonPlaceHolderService.restTemplateWithErrorHandler)
        .expect(requestTo(URL))
        .andExpect(method(HttpMethod.GET));
  }

  //  @Test
  public void response4xx() {
    responseActions.andRespond(withBadRequest());
    assertThatThrownBy(() -> todoJsonPlaceHolderService.findAll())
        .hasCauseInstanceOf(CustomException.class);
  }

  //  @Test
  public void response5xx() {
    responseActions.andRespond(withServerError());
    assertThatThrownBy(() -> todoJsonPlaceHolderService.findAll())
        .hasCauseInstanceOf(CustomException.class);
  }

  @Autowired
  private MockRestServiceServer server;
  @Autowired
  private RestTemplateBuilder builder;

  @Test
  public void givenRemoteApiCall_when404Error_thenThrowNotFound() {
    Assertions.assertNotNull(this.builder);
    Assertions.assertNotNull(this.server);

    RestTemplate restTemplate = this.builder
        .errorHandler(new CustomResponseErrorHandler())
        .build();

    this.server
        .expect(ExpectedCount.once(), requestTo("/bars/4242"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.NOT_FOUND));

    Assertions.assertThrows(CustomException.class, () -> {
      Todo response = restTemplate.getForObject("/todos/4242", Todo.class);
    });
  }
}
