package com.nagarro.BookManagement.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.BookManagement.csv.CsvService;
import com.nagarro.BookManagement.dto.AuthorRequestDTO;
import com.nagarro.BookManagement.dto.AuthorResponseDTO;
import com.nagarro.BookManagement.entity.Author;
import com.nagarro.BookManagement.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final CsvService csvService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(
            @Valid @RequestBody AuthorRequestDTO authorDTO) {

        Author author = Author.builder()
                .name(authorDTO.getName())
                .email(authorDTO.getEmail())
                .build();

        Author savedAuthor = authorService.saveAuthor(author);

        AuthorResponseDTO responseDTO = AuthorResponseDTO.builder()
                .id(savedAuthor.getId())
                .name(savedAuthor.getName())
                .email(savedAuthor.getEmail())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {

        List<AuthorResponseDTO> authors = authorService.getAllAuthors()
                .stream()
                .map(author -> AuthorResponseDTO.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .email(author.getEmail())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(
            @PathVariable Long id) {

        Author author = authorService.getAuthorById(id);

        AuthorResponseDTO responseDTO = AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDTO authorDTO) {

        Author author = Author.builder()
                .name(authorDTO.getName())
                .email(authorDTO.getEmail())
                .build();

        Author updatedAuthor = authorService.updateAuthor(id, author);

        AuthorResponseDTO responseDTO = AuthorResponseDTO.builder()
                .id(updatedAuthor.getId())
                .name(updatedAuthor.getName())
                .email(updatedAuthor.getEmail())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return ResponseEntity.ok("Author deleted successfully");
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportAuthors() {

        ByteArrayInputStream csvData =
                csvService.authorsToCsv(
                        authorService.getAllAuthors());

        InputStreamResource file =
                new InputStreamResource(csvData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=authors.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importAuthors(
            @RequestParam("file") MultipartFile file) {

        try {

            List<Author> authors =
                    csvService.csvToAuthors(file);

            for (Author author : authors) {
                authorService.saveAuthor(author);
            }

            return ResponseEntity.ok(
                    "Authors imported successfully");

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body("Error importing CSV");
        }
    }
}