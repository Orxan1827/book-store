package com.example.bookstore.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BookOrderResponse {

    private Integer id;
    private String userName;
    private List<Integer> bookList;
    private BigDecimal totalPrice;
}
