package com.nagarro.BookManagement.exception;

public class DuplicateAuthorException extends RuntimeException {

    public DuplicateAuthorException(String message) {
        super(message);
    }
}