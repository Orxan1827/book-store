package com.example.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.HashMap;
import java.util.Map;
import static com.example.bookstore.exception.ExceptionConstant.BOOK_NOT_FOUND;
import static com.example.bookstore.exception.ExceptionConstant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(BookNotFoundException ex) {
        var response = ExceptionResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(BOOK_NOT_FOUND.getMessage()).build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getMessage()).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        return ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }
}
