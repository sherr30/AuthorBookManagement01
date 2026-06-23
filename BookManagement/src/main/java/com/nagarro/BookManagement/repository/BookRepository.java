package com.nagarro.BookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.BookManagement.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

}