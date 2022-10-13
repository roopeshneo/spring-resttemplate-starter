package com.learn.spring.rest.restapi.exception;


import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;

@Slf4j
public class CustomResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
    HttpStatus status = clientHttpResponse.getStatusCode();
    return status.is4xxClientError() || status.is5xxServerError();
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
    HttpHeaders headers = response.getHeaders();
    MediaType contentType = headers.getContentType();
    switch (statusCode.series()) {
      case CLIENT_ERROR:
        throw new HttpClientErrorException(statusCode, response.getStatusText(),
            response.getHeaders(), FileCopyUtils.copyToByteArray(response.getBody()),
            contentType.getCharset());
      case SERVER_ERROR:
        throw new HttpServerErrorException(statusCode, response.getStatusText(),
            response.getHeaders(), FileCopyUtils.copyToByteArray(response.getBody()),
            contentType.getCharset());
      default:
//                throw new RestClientException("Unknown status code [" + statusCode + "]");
        String responseAsString = toString(response.getBody());
        log.error("ResponseBody: {}", responseAsString);
        throw new CustomException(responseAsString);
    }

  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
      throws IOException {
    String responseAsString = toString(response.getBody());
    log.error("URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, responseAsString);
    throw new CustomException(responseAsString);
  }

  String toString(InputStream inputStream) {
    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  static class CustomException extends IOException {
    public CustomException(String message) {
      super(message);
    }
  }
}