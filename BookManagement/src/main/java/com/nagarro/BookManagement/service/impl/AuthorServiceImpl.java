package com.nagarro.BookManagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.BookManagement.entity.Author;
import com.nagarro.BookManagement.repository.AuthorRepository;
import com.nagarro.BookManagement.service.AuthorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nagarro.BookManagement.exception.AuthorNotFoundException;
import com.nagarro.BookManagement.exception.DuplicateAuthorException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author saveAuthor(Author author) {

        log.info("Attempting to save author with email: {}", author.getEmail());

        if (authorRepository.existsByEmail(author.getEmail())) {

            log.warn("Duplicate author email found: {}", author.getEmail());

            throw new DuplicateAuthorException("Author already exists");
        }

        Author savedAuthor = authorRepository.save(author);

        log.info("Author saved successfully with id: {}", savedAuthor.getId());

        return savedAuthor;
    }

    @Override
    public List<Author> getAllAuthors() {

        log.info("Fetching all authors");

        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {

        log.info("Fetching author with id: {}", id);

        return authorRepository.findById(id)
                .orElseThrow(() -> {

                    log.error("Author not found with id: {}", id);

                    return new AuthorNotFoundException(
                            "Author not found with id: " + id);
                });
    }

    @Override
    public Author updateAuthor(Long id, Author author) {

        log.info("Updating author with id: {}", id);

        Author existingAuthor = getAuthorById(id);

        existingAuthor.setName(author.getName());
        existingAuthor.setEmail(author.getEmail());

        Author updatedAuthor = authorRepository.save(existingAuthor);

        log.info("Author updated successfully with id: {}", id);

        return updatedAuthor;
    }

    @Override
    public void deleteAuthor(Long id) {

        log.info("Deleting author with id: {}", id);

        Author author = getAuthorById(id);

        authorRepository.delete(author);

        log.info("Author deleted successfully with id: {}", id);
    }
}