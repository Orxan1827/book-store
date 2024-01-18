package com.example.bookstore.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {

    private int code;
    private String message;
}
