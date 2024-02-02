package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTest {

    private  BookService bookService;
    private  BookRepository bookRepository;
    private Book book;


    @BeforeEach
    public void setUp() throws Exception {
        bookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookService(bookRepository);

        book = Book.builder()
                .id(1).name("Titanik")
                .price(BigDecimal.valueOf(250))
                .stock(10)
                .author("Cek").build();
    }

    @Test
    void whenFindByBookIdCalledById_itShouldReturnOptionalBook() {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = (bookService.findByBookId(1));

        assertEquals(book, result);

        Mockito.verify(bookRepository, Mockito.times(1)).findById(1);

    }
}