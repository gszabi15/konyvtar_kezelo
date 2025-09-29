package org.gszabi15.controller;

import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.gszabi15.service.BookService;

import java.util.List;
import java.util.Optional;

public class BookController {

    private final BookService bookservice;

    public BookController(BookService bookservice) {
        this.bookservice = bookservice;
    }

    public List<BookDto> getAllBooks() {
        return bookservice.getAllBooks();
    }

    public BookDto createBook(BookDto bookDto, boolean keepId) {
        return bookservice.createBook(bookDto, keepId);
    }

    public BookDto updateBook(String id, BookDto bookDto) {
        return bookservice.updateBook(id, bookDto);
    }

    public boolean deleteBook(String id) {
        return bookservice.deleteBook(id);
    }

    public BookDto getBookById(String id) {
        return bookservice.getBookById(id);
    }

    public boolean saveToCSV(String filePath) {
        return bookservice.saveToCSV(filePath);
    }

    public boolean loadFromCSV(String filePath) {
        return bookservice.loadFromCSV(filePath);
    }

    public String generateUniqueId() {
        return bookservice.generateUniqueId();
    }

}