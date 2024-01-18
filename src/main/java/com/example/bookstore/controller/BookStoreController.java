package com.example.bookstore.controller;

import com.example.bookstore.dto.BookOrderRequest;
import com.example.bookstore.dto.BookOrderResponse;
import com.example.bookstore.entity.Order;
import com.example.bookstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bookstore")
public class BookStoreController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BookOrderResponse>putAnOrder(@Valid @RequestBody BookOrderRequest bookOrderRequest){
        BookOrderResponse response = orderService.putAnOrder(bookOrderRequest);
        return ResponseEntity.ok(response);
    }
}
