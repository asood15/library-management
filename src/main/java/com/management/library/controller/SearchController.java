package com.management.library.controller;

import com.management.library.service.AuthorService;
import com.management.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @QueryMapping(name="search")
    public List<Object> findBookORAuthorByText(@Argument String text) {
        List<Object> results = new ArrayList<>();
        results.addAll(bookService.searchBookByText(text));
        results.addAll(authorService.searchAuthorByText(text));

        return results;
    }
}
