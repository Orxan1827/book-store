package com.example.bookstore.mapper;

import com.example.bookstore.dto.BookOrderResponse;
import com.example.bookstore.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public BookOrderResponse mapOrderToResponse(Order order, List<Integer> bookList){
       BookOrderResponse response = BookOrderResponse
               .builder()
               .id(order.getId())
               .bookList(bookList)
               .totalPrice(order.getTotalPrice())
               .userName(order.getUserName())
               .build();
       return response;
    }
}
