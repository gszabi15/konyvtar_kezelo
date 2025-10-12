package org.gszabi15.controller;

import org.gszabi15.model.dto.BookDto;
import org.gszabi15.service.BookService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookRestController {
    private final BookService service;

    @GetMapping
    public Page<BookDto> getAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return service.getAllPaginated(page, size);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable("id") String id) {
        return service.getById(id);
    }

    @PostMapping
    public BookDto create(@RequestBody BookDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable("id") String id, @RequestBody BookDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
