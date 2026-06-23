package com.nagarro.BookManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarro.BookManagement.entity.Author;
import com.nagarro.BookManagement.repository.AuthorRepository;
import com.nagarro.BookManagement.service.impl.AuthorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void testGetAuthorById() {

        Author author = new Author();

        author.setId(1L);
        author.setName("Sreyansh");
        author.setEmail("sreyansh@gmail.com");

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));

        Author result =
                authorService.getAuthorById(1L);

        assertEquals("Sreyansh",
                result.getName());
    }
}