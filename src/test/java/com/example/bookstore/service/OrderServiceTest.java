package com.example.bookstore.service;

import com.example.bookstore.dto.BookOrderRequest;
import com.example.bookstore.dto.BookOrderResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private BookService bookService;
    private OrderMapper orderMapper;
    private Book book;
    private Order order;
    private BookOrderRequest bookOrderRequest;
    private BigDecimal totalPrice;
    private BookOrderResponse bookOrderResponse;

    @BeforeEach
    public void setUp() throws Exception {

        orderRepository = Mockito.mock(OrderRepository.class);
        bookService = Mockito.mock(BookService.class);
        orderMapper = Mockito.mock(OrderMapper.class);
        orderService = new OrderService(orderRepository, bookService, orderMapper);
        book = Book.builder()
                .id(1).name("Titanik")
                .price(BigDecimal.valueOf(250))
                .stock(10)
                .author("Cek").build();

        bookOrderRequest = BookOrderRequest.builder()
                .userName("Orxan")
                .bookIdList(List.of(1)).build();

        order = Order.builder()
                .id(1)
                .totalPrice(totalPrice)
                .userName(bookOrderRequest.getUserName()).build();

        bookOrderResponse = BookOrderResponse.builder()
                .id(1).totalPrice(totalPrice)
                .userName("Orxan")
                .bookList(bookOrderRequest.getBookIdList()).build();

        totalPrice = BigDecimal.valueOf(250);
    }

    @Test
    public void whenPutOnOrderCalledWithValidBookOrderRequest_itShouldReturnValidBookOrderResponse() {
        Mockito.when(bookService.findByBookId(1)).thenReturn(book);
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);
        Mockito.when(orderMapper.mapOrderToResponse(order, bookOrderRequest.getBookIdList())).thenReturn(bookOrderResponse);

        BookOrderResponse result = orderService.putAnOrder(bookOrderRequest);

        assertNotNull(result);
        assertEquals(bookOrderResponse, result);

        Mockito.verify(bookService, Mockito.times(1)).findByBookId(1);
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(orderMapper).mapOrderToResponse(order, bookOrderRequest.getBookIdList());
    }

    @Test
    public void whenPutOnOrderCalledWithValidBookOrderRequestWithNegativePrice_itShouldReturnValidBookOrderResponse() {
        book.setPrice(BigDecimal.valueOf(250));
        Mockito.when(bookService.findByBookId(1)).thenReturn(book);
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);
        Mockito.when(orderMapper.mapOrderToResponse(order, bookOrderRequest.getBookIdList())).thenReturn(bookOrderResponse);
        BookOrderResponse result = orderService.putAnOrder(bookOrderRequest);

        assertNotNull(result);
        assertEquals(bookOrderResponse.getTotalPrice(), result.getTotalPrice());

        Mockito.verify(bookService, Mockito.times(1)).findByBookId(1);
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(orderMapper).mapOrderToResponse(order, bookOrderRequest.getBookIdList());
    }
}