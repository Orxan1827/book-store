package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookOrderRequest {

    @NotNull(message = "bookIdList must not be null")
    private List<Integer> bookIdList;

    @NotBlank(message = "username must not be blank")
    private String userName;
}
