package org.gszabi15.service;

import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.mapper.BookMapper;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookService(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public void createBook(BookDto bookDto) {
        bookRepository.save(mapper.bookDtoToBook(bookDto));
    }

    public void updateBook(String id, BookDto bookDto) {
        bookRepository.update(id, mapper.bookDtoToBook(bookDto));
    }

    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }

    public BookDto getBookById(String id) {
        return bookRepository.getById(id).map(mapper::bookToBookDto).orElse(null);
    }

    public List<BookDto> getAllBooks() {
        return mapper.bookListToBookDtoList(bookRepository.getAllBooks());
    }

    public String generateUniqueId() {
        return bookRepository.generateUniqueId();
    }
}
