package com.nagarro.BookManagement.service;

import java.util.List;

import com.nagarro.BookManagement.entity.Author;

public interface AuthorService {

    Author saveAuthor(Author author);

    List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    Author updateAuthor(Long id, Author author);

    void deleteAuthor(Long id);
}