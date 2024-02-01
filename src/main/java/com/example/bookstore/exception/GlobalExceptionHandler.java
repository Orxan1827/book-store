package com.example.bookstore.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.bookstore.exception.ExceptionConstant.BOOK_NOT_FOUND;
import static com.example.bookstore.exception.ExceptionConstant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(BookNotFoundException ex) {
        var response = ExceptionResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(BOOK_NOT_FOUND.getMessage()).build();
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            errors.put(((FieldError) err).getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest()
                .body(errors);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ExceptionResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getMessage()).build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        errors.put("errorCode", status.value());
        errors.put("httpStatus", status);

        return ResponseEntity.status(status).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("errorMessage", ex.getParameterName() + " parameter is missing");
        errors.put("errorCode", status.value());
        errors.put("httpStatus", status);

        return ResponseEntity.status(status).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        details.add(builder.toString());

        Map<String, Object> errors = new HashMap<>();
        errors.put("errorMessage", details);
        errors.put("errorCode", status.value());
        errors.put("httpStatus", status);

        return ResponseEntity.status(status).body(errors);

    }
}
