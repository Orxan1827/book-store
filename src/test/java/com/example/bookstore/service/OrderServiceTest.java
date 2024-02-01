package com.example.bookstore.service;

import com.example.bookstore.dto.BookOrderRequest;
import com.example.bookstore.dto.BookOrderResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.repository.OrderRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.List;


public class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;
    private BookService bookService;
    private OrderMapper orderMapper;

    @Before("")
    public void setUp() throws Exception {

        orderRepository = Mockito.mock(OrderRepository.class);
        bookService = Mockito.mock(BookService.class);
        orderMapper = Mockito.mock(OrderMapper.class);

        orderService = new OrderService(orderRepository,bookService,orderMapper);
    }

    @Test
    public void whenPutOnOrderCalledWithValidBookOrderRequest_itShouldReturnValidBookOrderResponse() {
        BookOrderRequest bookOrderRequest = new BookOrderRequest();
        bookOrderRequest.setUserName("Orxan");
        bookOrderRequest.setBookIdList(List.of(1));

        BigDecimal totalPrice = BigDecimal.valueOf(20);

        Book book = Book.builder()
                .id(1)
                .name("Titanik")
                .price(BigDecimal.valueOf(10))
                .stock(10)
                .author("Cek")
                .build();


        Order order = Order.builder()
                .id(1)
                .totalPrice(totalPrice)
                .userName(bookOrderRequest.getUserName())
                .build();

        BookOrderResponse bookOrderResponse = BookOrderResponse
                .builder()
                .id(1)
                .totalPrice(totalPrice)
                .userName(bookOrderRequest.getUserName())
                .bookList(bookOrderRequest.getBookIdList())
                .build();

        Mockito.when(bookService.findByBookId(1)).thenReturn(book);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderMapper.mapOrderToResponse(order,bookOrderRequest.getBookIdList())).thenReturn(bookOrderResponse);

        BookOrderResponse result = orderService.putAnOrder(bookOrderRequest);
        Assert.assertEquals(result,bookOrderResponse);
        Mockito.verify(bookService).findByBookId(1);
        Mockito.verify(orderRepository).save(order);
        Mockito.verify(orderMapper).mapOrderToResponse(order,bookOrderRequest.getBookIdList());
    }
}