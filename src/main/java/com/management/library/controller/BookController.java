package com.management.library.controller;

import com.management.library.entity.Book;
import com.management.library.entity.BookInput;
import com.management.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @QueryMapping(name = "books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @QueryMapping(name="book")
    public Optional<Book> findById(@Argument Long id) {
        return bookService.findById(id);
    }

    @MutationMapping(name="addBook")
    public Book addBook(@Argument BookInput bookInput) {
        return bookService.addBook(bookInput);
    }
}
