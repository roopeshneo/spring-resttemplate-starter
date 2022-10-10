package com.learn.spring.rest.restapi.exception;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.learn.spring.rest.restapi.exception.CustomResponseErrorHandler.CustomException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public String handleXXException(HttpClientErrorException e) {
    log.error("log HttpClientErrorException: ", e);
    return "HttpClientErrorException_message";
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public String handleXXException(HttpServerErrorException e) {
    log.error("log HttpServerErrorException: ", e);
    return "HttpServerErrorException_message";
  }
  // catch unknown error
  @ExceptionHandler(Exception.class)
  public String handleException(Exception e) {
    log.error("log unknown error", e);
    return "unknown_error_message";
  }

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<Object> handleCustom(
      CustomException ex) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      EntityNotFoundException ex) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(BAD_REQUEST);
    apiError.setMessage(String.format("The parameter '%s' of value '%s' " +
            "could not be converted to type '%s'", ex.getName(), ex.getValue(),
        ex.getRequiredType().getSimpleName()));
    apiError.setDebugMessage(ex.getMessage());
    IApiWarningBean warningBean = new ApiContractWarning(request.getContextPath(),
        request.getContextPath());
    apiError.setSubWarnings(Collections.singletonList(warningBean));
    return buildResponseEntity(apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  //other exception handlers below

}
