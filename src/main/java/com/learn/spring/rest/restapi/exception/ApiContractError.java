package com.learn.spring.rest.restapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiContractError implements ApiErrorDetail {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiContractError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
