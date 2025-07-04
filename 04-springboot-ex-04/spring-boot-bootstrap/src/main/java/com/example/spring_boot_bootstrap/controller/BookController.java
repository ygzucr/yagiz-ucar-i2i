package com.example.spring_boot_bootstrap.controller;

import com.example.spring_boot_bootstrap.exception.BookIdMismatchException;
import com.example.spring_boot_bootstrap.exception.BookNotFoundException;
import com.example.spring_boot_bootstrap.model.Book;
import com.example.spring_boot_bootstrap.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books API", description = "CRUD operations for books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Operation(
            summary = "List all books",
            description = "Returns a list of all books in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
            }
    )
    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Operation(
            summary = "Find books by title",
            description = "Returns all books whose title matches the given title",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
            }
    )
    @GetMapping("/title/{bookTitle}")
    public List<Book> findByTitle(
            @Parameter(description = "Title to search for", required = true)
            @PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Returns a single book",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @GetMapping("/{id}")
    public Book findOne(
            @Parameter(description = "ID of the book to retrieve", required = true)
            @PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Operation(
            summary = "Create a new book",
            description = "Adds a new book to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book successfully created")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Book object to create",
                    required = true
            )
            @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @Operation(
            summary = "Delete a book",
            description = "Deletes the book with the given ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Book deleted"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID of the book to delete", required = true)
            @PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @Operation(
            summary = "Update an existing book",
            description = "Updates the book with the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book successfully updated"),
                    @ApiResponse(responseCode = "400", description = "ID in payload does not match path"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @PutMapping("/{id}")
    public Book updateBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated book object",
                    required = true
            )
            @RequestBody Book book,
            @Parameter(description = "ID of the book to update", required = true)
            @PathVariable Long id) {

        if (!book.getId().equals(id)) {
            throw new BookIdMismatchException();
        }

        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return bookRepository.save(book);
    }

}
