package com.nagarro.BookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.BookManagement.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByEmail(String email);

}