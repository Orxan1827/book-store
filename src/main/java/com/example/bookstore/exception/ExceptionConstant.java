package com.example.bookstore.exception;

import lombok.Getter;

@Getter
public enum ExceptionConstant {

    BOOK_NOT_FOUND(404,"can not find book with given id"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(400,"argument type not valid");

    private final int code;
    private final String message;
    ExceptionConstant(int code, String message) {
        this.code = code;
        this.message =message;
    }
}
