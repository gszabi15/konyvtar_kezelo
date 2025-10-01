package org.gszabi15.service;


import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public record BookService(BookRepository bookRepository) {

    public void createBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        bookRepository.save(book);
    }

    public void updateBook(String id, BookDto bookDto) {
        bookRepository.update(id, convertToEntity(bookDto));
    }

    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }

    public Optional<BookDto> getBookById(String id) {
        return bookRepository.getById(id).map(this::convertToDto);
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

    public String generateUniqueId() {
        return bookRepository.generateUniqueId();
    }
}