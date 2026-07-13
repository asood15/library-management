package com.management.library.controller;

import com.management.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookControllerTests {

    private final GraphQlTester graphQlTester;

    @Autowired
    BookControllerTests(ExecutionGraphQlService graphQlService) {
        this.graphQlTester = ExecutionGraphQlServiceTester.builder(graphQlService).build();
    }

    @Test
    void shouldFindAllBooks() {
        String query = """
                query {
                    books {
                        id
                        title
                    }
                }
                """;

        graphQlTester.document(query)
                .execute()
                .errors()
                .verify()
                .path("books")
                .entityList(Book.class)
                .satisfies(books -> {
                    assertThat(books).isNotEmpty();
                    assertThat(books.getFirst().getTitle()).isEqualTo("Cloud Native Java");
                });
    }

    @Test
    void shouldFindBookById() {
        String query = """
                query($id: ID!) {
                    book(id: $id) {
                        id
                        title
                    }
                }
                """;

        graphQlTester.document(query)
                .variable("id", 1L)
                .execute()
                .errors()
                .verify()
                .path("book")
                .entity(Book.class)
                .satisfies(book -> {
                    assertThat(book.getId()).isEqualTo(1L);
                    assertThat(book.getTitle()).isEqualTo("Cloud Native Java");
                });
    }

    @Test
    void shouldAddBook() {
        String mutation = """
                mutation($input: BookInput!) {
                    addBook(bookInput: $input) {
                        id
                        title
                        author {
                            id
                            name
                        }
                    }
                }
                """;

        graphQlTester.document(mutation)
                .variable("input", Map.of("title", "New Book", "authorId", 1L))
                .execute()
                .errors()
                .verify()
                .path("addBook")
                .entity(Book.class)
                .satisfies(book -> {
                    assertThat(book.getTitle()).isEqualTo("New Book");
                    assertThat(book.getAuthor().getName()).isEqualTo("Josh Long");
                });
    }
}
