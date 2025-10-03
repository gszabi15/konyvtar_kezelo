package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.service.BookService;

import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public record BookController(BookService bookservice) {

    public List<BookDto> getAllBooks() {
        return bookservice.getAllBooks();
    }

    public void createBook(BookDto bookDto) {
        bookservice.createBook(bookDto);
    }

    public void updateBook(String id, BookDto bookDto) {
        bookservice.updateBook(id, bookDto);
    }

    public boolean deleteBook(String id) {
        return bookservice.deleteBook(id);
    }

    public BookDto getBookById(String id) {
        return bookservice.getBookById(id).orElse(null);
    }

    public String generateUniqueId() {
        return bookservice.generateUniqueId();
    }

}