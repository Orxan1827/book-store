package com.example.bookstore;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class BookStoreApplication implements CommandLineRunner {

    private final BookRepository bookRepository;

    public BookStoreApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Book book = Book.builder()
                .name("Yuzuklerin efendisi")
                .author("Tolkien")
                .price(BigDecimal.valueOf(90))
                .stock(10)
                .build();
        Book book1 = Book.builder()
                .name("Baliklar")
                .author("Jon Waswing")
                .price(BigDecimal.valueOf(120))
                .stock(12)
                .build();
        Book book2 = Book.builder()
                .name("Cuma Pazari")
                .author("Ahmet Bulut")
                .price(BigDecimal.valueOf(100))
                .stock(15)
                .build();
        bookRepository.saveAll(Arrays.asList(book, book1, book2));
    }
}
