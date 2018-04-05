package com.sdsu.edu.cms.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiErrorException extends RuntimeException{

    public ApiErrorException(String message) {
        super(message);
    }
}
