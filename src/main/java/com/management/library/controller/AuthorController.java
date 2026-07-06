package com.management.library.controller;

import com.management.library.entity.Author;
import com.management.library.service.AuthorInput;
import com.management.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @QueryMapping(name= "author")
    public Optional<Author> getAuthorById(@Argument Long id) {
        return authorService.getAuthorById(id);
    }

    @MutationMapping(name="addAuthor")
    public Author addAuthor(@Argument AuthorInput authorInput) {
        return authorService.addAuthor(authorInput);
    }
}
