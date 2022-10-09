package com.learn.spring.rest.restapi.config;

import com.learn.spring.rest.restapi.customizer.CustomRestTemplateCustomizer;
import com.learn.spring.rest.restapi.exception.CustomResponseErrorHandler;
import com.learn.spring.rest.restapi.interceptors.CustomHttpRequestResponseLogInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    private final int TIMEOUT = 500;



    @Bean
    public RestTemplate restTemplateWithErrorHandler(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());

        return restTemplateBuilder.requestFactory(() -> factory)
                .additionalCustomizers(new CustomRestTemplateCustomizer())
                .errorHandler(new CustomResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .setReadTimeout(Duration.ofMillis(TIMEOUT)).build();
    }

//    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }
}
