package com.example.spring_boot_bootstrap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException() {
        super("Book ID in path and payload do not match");
    }
}
