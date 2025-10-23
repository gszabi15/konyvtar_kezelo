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
import java.util.UUID;

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
        return repo.findById(UUID.fromString(id)).map(mapper::bookToDto)
                .orElseThrow(() -> new BookNotAvailableException("Book not found with id: " + id));
    }

    public BookDto create(BookDto dto) {
        Book book = mapper.dtoToBook(dto);
        book.setId(null);
        book.setAvailable(true);
        repo.save(book);
        return mapper.bookToDto(book);
    }

    public BookDto update(String id, BookDto dto) {
        Optional<Book> opt = repo.findById(UUID.fromString(id));
        if (opt.isEmpty()) {
            throw new BookNotAvailableException("Book not found with id: " + id);
        }
        opt.get().setTitle(dto.getTitle());
        opt.get().setAuthor(dto.getAuthor());
        opt.get().setAvailable(dto.isAvailable());
        repo.save(opt.get());

        return mapper.bookToDto(opt.get());
    }

    public void delete(String id) {
        if (!repo.existsById(UUID.fromString(id))) {
            throw new BookNotAvailableException("Book not found with id: " + id);
        }
        repo.deleteById(UUID.fromString(id));
    }
}
