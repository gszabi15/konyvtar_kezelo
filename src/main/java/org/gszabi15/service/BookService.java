package org.gszabi15.service;

import org.gszabi15.model.dto.BookDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.entity.Book;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.exceptions.BookNotAvailableException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repo;
    private final EntityMapper mapper;

    public Page<BookDto> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return repo.findAll(pageable).map(mapper::bookToDto);
    }

    public BookDto getById(String id) {
        return repo.findById(id).map(mapper::bookToDto)
                .orElseThrow(() -> new BookNotAvailableException("Book not found with id: " + id));
    }

    public BookDto create(BookDto dto) {
        Book book = mapper.dtoToBook(dto);
        book.setAvailable(true);
        repo.save(book);
        return mapper.bookToDto(book);
    }

    public BookDto update(String id, BookDto dto) {
        Optional<Book> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new BookNotAvailableException("Book not found with id: " + id);
        }
        repo.deleteById(id);
        Book b = opt.get();
        b.setTitle(dto.getTitle());
        b.setAuthor(dto.getAuthor());
        b.setAvailable(dto.isAvailable());
        repo.save(b);
        return mapper.bookToDto(b);
    }

    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new BookNotAvailableException("Book not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
