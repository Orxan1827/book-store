package com.example.bookstore.service;

import com.example.bookstore.dto.BookOrderRequest;
import com.example.bookstore.dto.BookOrderResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;

    private final OrderMapper orderMapper;

    public BookOrderResponse putAnOrder(BookOrderRequest bookOrderRequest) {
        List<Book> bookList = bookOrderRequest.getBookIdList()
                .stream()
                .map(bookService::findByBookId)
                .toList();

        BigDecimal totalPrice = bookList
                .stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .totalPrice(totalPrice)
                .userName(bookOrderRequest.getUserName())
                .build();

        var savedOrder = orderRepository.save(order);
        return orderMapper.mapOrderToResponse(savedOrder, bookOrderRequest.getBookIdList());
    }

}
