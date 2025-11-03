package org.gszabi15.controller;

import org.gszabi15.model.dto.BookDto;
import org.gszabi15.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRestControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    private BookDto book;

    @BeforeEach
    void setUp() {
        book = new BookDto("1", "Test Title", "Test Author", true);
    }

    @Test
    void getAll_shouldReturnPage() {
        Page<BookDto> page = new PageImpl<>(List.of(book));
        when(bookService.getAllPaginated(0, 10)).thenReturn(page);

        Page<BookDto> result = bookRestController.getAll(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(book.getTitle(), result.getContent().getFirst().getTitle());
        verify(bookService).getAllPaginated(0, 10);
    }

    @Test
    void getById_shouldReturnBook() {
        when(bookService.getById("1")).thenReturn(book);

        BookDto result = bookRestController.getById("1");

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService).getById("1");
    }

    @Test
    void create_shouldReturnBook() {
        when(bookService.create(book)).thenReturn(book);

        BookDto result = bookRestController.create(book);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService).create(book);
    }

    @Test
    void update_shouldReturnBook() {
        when(bookService.update("1", book)).thenReturn(book);

        BookDto result = bookRestController.update("1", book);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService).update("1", book);
    }

    @Test
    void delete_shouldCallService() {
        doNothing().when(bookService).delete("1");

        bookRestController.delete("1");

        verify(bookService).delete("1");
    }
}