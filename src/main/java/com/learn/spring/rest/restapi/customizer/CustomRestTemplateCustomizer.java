package com.learn.spring.rest.restapi.customizer;


import com.learn.spring.rest.restapi.interceptors.CustomHttpRequestLogInterceptor;
import com.learn.spring.rest.restapi.interceptors.HttpHeaderModifierInterceptor;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new HttpHeaderModifierInterceptor());
        restTemplate.getInterceptors().add(new CustomHttpRequestLogInterceptor());
    }
}