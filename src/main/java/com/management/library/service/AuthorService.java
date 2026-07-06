package com.management.library.service;

import com.management.library.entity.Author;
import com.management.library.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Optional<Author> getAuthorById(long authorId) {
        return authorRepository.findById(authorId);
    }

    public Author addAuthor(AuthorInput authorInput) {
        Author author = new Author();
        author.setName(authorInput.name());

        return authorRepository.save(author);
    }
}
