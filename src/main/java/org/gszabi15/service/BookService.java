package org.gszabi15.service;

import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public record BookService(BookRepository bookRepository, ModelMapper modelMapper) {

    public void createBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        bookRepository.save(book);
    }

    public void updateBook(String id, BookDto bookDto) {
        bookRepository.update(id, modelMapper.map(bookDto, Book.class));
    }

    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }

    public Optional<BookDto> getBookById(String id) {
        return bookRepository.getById(id)
                .map(book -> modelMapper.map(book, BookDto.class));
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks()
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();
    }

    public String generateUniqueId() {
        return bookRepository.generateUniqueId();
    }
}
