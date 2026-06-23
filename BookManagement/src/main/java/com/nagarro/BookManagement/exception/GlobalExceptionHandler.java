package com.nagarro.BookManagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.BookManagement.exception.AuthorNotFoundException;
import com.nagarro.BookManagement.exception.BookNotFoundException;
import com.nagarro.BookManagement.exception.DuplicateAuthorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
                  errors.put(
                          error.getField(),
                          error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFound(
            AuthorNotFoundException ex) {

        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(
            BookNotFoundException ex) {

        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(DuplicateAuthorException.class)
    public ResponseEntity<String> handleDuplicateAuthor(
            DuplicateAuthorException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Cannot delete author because the author is linked with one or more books.");
    }
}