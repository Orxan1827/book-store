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

        BigDecimal newTotalPrice = null;
        if (totalPrice.compareTo(BigDecimal.valueOf(200)) >= 0) {
            newTotalPrice = addDiscount(totalPrice, BigDecimal.valueOf(80), BigDecimal.valueOf(100));
        }

        Order order = Order.builder()
                .totalPrice(newTotalPrice != null ? newTotalPrice : totalPrice)
                .userName(bookOrderRequest.getUserName())
                .build();

        return orderMapper.mapOrderToResponse(orderRepository.save(order), bookOrderRequest.getBookIdList());
    }

    private BigDecimal addDiscount(BigDecimal totalPrice, BigDecimal calculationNum, BigDecimal calculationNum2) {
        return totalPrice.multiply(calculationNum).divide(calculationNum2);
    }

}
