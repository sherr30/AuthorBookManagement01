package com.nagarro.BookManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarro.BookManagement.entity.Book;
import com.nagarro.BookManagement.repository.BookRepository;
import com.nagarro.BookManagement.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testGetBookById() {

        Book book = new Book();

        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("9780132350884");
        book.setPrice(599.0);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        Book result =
                bookService.getBookById(1L);

        assertEquals("Clean Code",
                result.getTitle());
    }
}