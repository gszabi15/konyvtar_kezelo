package org.gszabi15.service;

import org.gszabi15.model.BookDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.Book;
import org.gszabi15.repository.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repo;
    private final EntityMapper mapper;

    public BookService(BookRepository repo, EntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Page<BookDto> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return repo.findAll(pageable).map(mapper::bookToDto);
    }

    public BookDto getById(String id) {
        return repo.findById(id).map(mapper::bookToDto).orElse(null);
    }

    public BookDto create(BookDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Book book = mapper.dtoToBook(dto);
        book.setAvailable(true);
        repo.save(book);
        return mapper.bookToDto(book);
    }

    public BookDto update(String id, BookDto dto) {
        Optional<Book> opt = repo.findById(id);
        if (opt.isPresent()) {
            Book b = opt.get();
            b.setTitle(dto.getTitle());
            b.setAuthor(dto.getAuthor());
            b.setAvailable(dto.isAvailable());
            repo.save(b);
            return mapper.bookToDto(b);
        }
        return null;
    }

    public boolean delete(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
