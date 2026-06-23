package com.nagarro.BookManagement.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.BookManagement.csv.CsvService;
import com.nagarro.BookManagement.dto.BookRequestDTO;
import com.nagarro.BookManagement.dto.BookResponseDTO;
import com.nagarro.BookManagement.entity.Author;
import com.nagarro.BookManagement.entity.Book;
import com.nagarro.BookManagement.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CsvService csvService;

    private BookResponseDTO mapToResponseDTO(Book book) {

        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .authorId(book.getAuthor() != null
                        ? book.getAuthor().getId()
                        : null)
                .authorName(book.getAuthor() != null
                        ? book.getAuthor().getName()
                        : null)
                .build();
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(
            @Valid @RequestBody BookRequestDTO bookDTO) {

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .price(bookDTO.getPrice())
                .author(
                        Author.builder()
                                .id(bookDTO.getAuthorId())
                                .build())
                .build();

        Book savedBook = bookService.saveBook(book);

        return ResponseEntity.ok(mapToResponseDTO(savedBook));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        List<BookResponseDTO> books = bookService.getAllBooks()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(
            @PathVariable Long id) {

        Book book = bookService.getBookById(id);

        return ResponseEntity.ok(mapToResponseDTO(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO bookDTO) {

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .price(bookDTO.getPrice())
                .author(
                        Author.builder()
                                .id(bookDTO.getAuthorId())
                                .build())
                .build();

        Book updatedBook = bookService.updateBook(id, book);

        return ResponseEntity.ok(mapToResponseDTO(updatedBook));
    }

    @PutMapping("/bulk-update")
    public ResponseEntity<List<Book>> bulkUpdateBooks(
            @RequestBody List<Book> books) {

        return ResponseEntity.ok(
                bookService.bulkUpdateBooks(books));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportBooks() {

        ByteArrayInputStream csvData =
                csvService.booksToCsv(
                        bookService.getAllBooks());

        InputStreamResource file =
                new InputStreamResource(csvData);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=books.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importBooks(
            @RequestParam("file") MultipartFile file) {

        try {

            List<Book> books =
                    csvService.csvToBooks(file);

            for (Book book : books) {
                bookService.saveBook(book);
            }

            return ResponseEntity.ok(
                    "Books imported successfully");

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body("Error importing CSV");
        }
    }
}