package com.example.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderResponse {

    private Integer id;
    private String userName;
    private List<Integer> bookList;
    private BigDecimal totalPrice;
}
