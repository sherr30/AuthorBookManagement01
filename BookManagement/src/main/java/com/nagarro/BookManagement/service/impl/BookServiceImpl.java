package com.nagarro.BookManagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.BookManagement.entity.Author;
import com.nagarro.BookManagement.entity.Book;
import com.nagarro.BookManagement.exception.AuthorNotFoundException;
import com.nagarro.BookManagement.exception.BookNotFoundException;
import com.nagarro.BookManagement.repository.AuthorRepository;
import com.nagarro.BookManagement.repository.BookRepository;
import com.nagarro.BookManagement.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book saveBook(Book book) {

        log.info("Saving book: {}", book.getTitle());

        Long authorId = book.getAuthor().getId();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(
                        "Author not found with id: " + authorId));

        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        log.info("Book saved successfully with id: {}", savedBook.getId());

        return savedBook;
    }

    @Override
    public List<Book> getAllBooks() {

        log.info("Fetching all books");

        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {

        log.info("Fetching book with id: {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() -> {

                    log.error("Book not found with id: {}", id);

                    return new BookNotFoundException(
                            "Book not found with id: " + id);
                });
    }

    @Override
    public Book updateBook(Long id, Book book) {

        log.info("Updating book with id: {}", id);

        Book existingBook = getBookById(id);

        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPrice(book.getPrice());

        if (book.getAuthor() != null) {

            Long authorId = book.getAuthor().getId();

            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException(
                            "Author not found with id: " + authorId));

            existingBook.setAuthor(author);
        }

        Book updatedBook = bookRepository.save(existingBook);

        log.info("Book updated successfully with id: {}", id);

        return updatedBook;
    }

    @Override
    public void deleteBook(Long id) {

        log.info("Deleting book with id: {}", id);

        Book book = getBookById(id);

        bookRepository.delete(book);

        log.info("Book deleted successfully with id: {}", id);
    }

    @Override
    public List<Book> bulkUpdateBooks(List<Book> books) {

        log.info("Bulk updating {} books", books.size());

        return bookRepository.saveAll(books);
    }
}