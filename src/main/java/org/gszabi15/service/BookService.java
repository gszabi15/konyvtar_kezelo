package org.gszabi15.service;
import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.config.BookMapper;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public record BookService(BookRepository bookRepository, BookMapper mapper) {

    public void createBook(BookDto bookDto) {
        Book book = mapper.bookDtoToBook(bookDto);
        bookRepository.save(book);
    }

    public void updateBook(String id, BookDto bookDto) {
        bookRepository.update(id, mapper.bookDtoToBook(bookDto));
    }

    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }

    public Optional<BookDto> getBookById(String id) {
        return bookRepository.getById(id)
                .map(mapper::bookToBookDto);
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks()
                .stream()
                .map(mapper::bookToBookDto)
                .toList();
    }

    public String generateUniqueId() {
        return bookRepository.generateUniqueId();
    }
}
