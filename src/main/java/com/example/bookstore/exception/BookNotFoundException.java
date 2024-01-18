package com.example.bookstore.exception;

import lombok.Data;

@Data
public class BookNotFoundException extends RuntimeException {

    private int code;

    public BookNotFoundException() {
    }

    public BookNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }
}
