package org.gszabi15.service;


import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto createBook(BookDto bookDto, boolean keepId) {
        Book book = convertToEntity(bookDto);
        Book savedBook = bookRepository.save(book, keepId);
        return convertToDto(savedBook);
    }

    public BookDto updateBook(String id, BookDto bookDto) {
        return convertToDto(bookRepository.update(id, convertToEntity(bookDto)));
    }

    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }

    public BookDto getBookById(String id) {
        return convertToDto(bookRepository.getById(id));
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks().stream().map(this::convertToDto).toList();
    }

    private BookDto convertToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor());
    }

    private Book convertToEntity(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor());
    }

    public boolean saveToCSV(String filePath) {
        return bookRepository.saveToCSV(filePath);
    }

    public boolean loadFromCSV(String filePath) {
        return bookRepository.loadFromCSV(filePath);
    }

    public String generateUniqueId() {
        return bookRepository.generateUniqueId();
    }
}