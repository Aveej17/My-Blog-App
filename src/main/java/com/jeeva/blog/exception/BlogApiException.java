package com.jeeva.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class BlogApiException extends RuntimeException{

    @Getter
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }


    List<Integer> j = new ArrayList<>();
}
