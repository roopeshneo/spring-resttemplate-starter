package com.learn.spring.rest.restapi.config;

import com.learn.spring.rest.restapi.interceptors.CustomHttpRequestResponseLogInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        //RestTemplate restTemplate = restTemplateBuilder.build(factory);
//        restTemplate.setInterceptors(Collections.singletonList(new CustomHttpRequestResponseLogInterceptor()));
        return restTemplateBuilder.build();
    }
}
