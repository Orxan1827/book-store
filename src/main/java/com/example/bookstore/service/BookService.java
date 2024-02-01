package com.example.bookstore.service;

import com.example.bookstore.dto.BookSearchRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book findByBookId(Integer bookId) {
         return bookRepository.findById(bookId)
                 .orElseThrow(BookNotFoundException::new);
    }

    public List<Book> getListBooks(BookSearchRequest searchRequest) {
       var searchBook= bookRepository.findAll(PageRequest.of(searchRequest.getPage(), searchRequest.getCount()))
                .getContent()
                .stream()
                .map(book -> book.builder()
                        .name(book.getName())
                        .price(book.getPrice())
                        .author(book.getAuthor())
                        .stock(book.getStock())
                        .build()).collect(Collectors.toList());
        return searchBook;
    }
}
