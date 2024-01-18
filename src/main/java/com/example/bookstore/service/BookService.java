package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book findByBookId(Integer bookId) {
         return bookRepository.findById(bookId)
                 .orElseThrow(BookNotFoundException::new);
    }
}
