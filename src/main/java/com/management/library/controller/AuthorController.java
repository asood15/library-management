package com.management.library.controller;

import com.management.library.entity.Author;
import com.management.library.entity.Book;
import com.management.library.repo.BookRepository;
import com.management.library.service.AuthorInput;
import com.management.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    @QueryMapping(name = "author")
    public Optional<Author> getAuthorById(@Argument Long id) {
        return authorService.getAuthorById(id);
    }

    @QueryMapping(name = "authors")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

   @BatchMapping(field = "books")
    public List<List<Book>> books(List<Author> authors) {
        List<Long> authorIds = authors.stream().map(Author::getId).toList();

        List<Book> books = bookRepository.findByAuthorIdIn(authorIds);

        Map<Long, List<Book>> authorIdToBooksMap = books.stream().collect(Collectors.groupingBy(book -> book.getAuthor().getId()));

        return authors.stream().map(author ->  authorIdToBooksMap.getOrDefault(author.getId(), Collections.emptyList())).toList();
    }

    @MutationMapping(name = "addAuthor")
    public Author addAuthor(@Argument AuthorInput authorInput) {
        return authorService.addAuthor(authorInput);
    }
}
