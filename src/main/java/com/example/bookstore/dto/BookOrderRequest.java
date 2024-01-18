package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BookOrderRequest {

    @NotNull(message = "bookIdList must not be null")
    private List<Integer> bookIdList;

    @NotBlank(message = "username must not be blank")
    private String userName;
}
