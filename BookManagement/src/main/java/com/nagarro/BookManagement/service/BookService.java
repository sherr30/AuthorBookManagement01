package com.nagarro.BookManagement.service;

import java.util.List;

import com.nagarro.BookManagement.entity.Book;

public interface BookService {

    Book saveBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> bulkUpdateBooks(List<Book> books);
}