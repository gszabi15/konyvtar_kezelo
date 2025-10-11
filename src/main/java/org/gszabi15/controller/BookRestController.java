package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.service.BookService;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/books")
public class BookRestController {
    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public Page<@NotNull BookDto> getAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return service.getAllPaginated(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NotNull BookDto> getById(@PathVariable("id") String id) {
        BookDto dto = service.getById(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<@NotNull BookDto> create(@RequestBody BookDto dto) {
        BookDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NotNull BookDto> update(@PathVariable("id") String id, @RequestBody BookDto dto) {
        BookDto updated = service.update(id, dto);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NotNull Void> delete(@PathVariable("id") String id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
