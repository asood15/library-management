package com.management.library.service;

import com.management.library.entity.Author;
import com.management.library.entity.Book;
import com.management.library.entity.BookInput;
import com.management.library.repo.AuthorRepository;
import com.management.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorService;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(BookInput bookInput) {
        Author author = authorService.findById(bookInput.authorId()).orElseThrow();
        var book = new Book();
        book.setTitle(bookInput.title());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public List<Book> searchBookByText(String text) {
        return bookRepository.findAllByTitleContainsIgnoreCase(text);
    }
}
