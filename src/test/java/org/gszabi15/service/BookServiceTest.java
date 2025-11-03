package org.gszabi15.service;

import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.entity.Book;
import org.gszabi15.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(UUID.randomUUID());
        book.setAuthor("Test Author");
        book.setTitle("Test Title");
        book.setAvailable(true);

        bookDto = new BookDto(book.getId().toString(), book.getTitle(), book.getAuthor(), book.isAvailable());
    }

    @Test
    void getAllPaginated_shouldReturnPageOfBooks() {
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllPaginated(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(book.getAuthor(), result.getContent().getFirst().getAuthor());
        verify(bookRepository).findAll(any(Pageable.class));
        verify(mapper).bookToDto(book);
    }

    @Test
    void getById_shouldReturnBookDto() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getById(book.getId().toString());

        assertNotNull(result);
        assertEquals(book.getAuthor(), result.getAuthor());
        verify(bookRepository).findById(book.getId());
        verify(mapper).bookToDto(book);
    }

    @Test
    void create_shouldReturnCreatedBookDto() {
        when(mapper.dtoToBook(bookDto)).thenReturn(book);
        when(mapper.bookToDto(book)).thenReturn(bookDto);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto result = bookService.create(bookDto);

        assertNotNull(result);
        assertEquals(book.getAuthor(), result.getAuthor());
        verify(bookRepository).save(book);
        verify(mapper).dtoToBook(bookDto);
        verify(mapper).bookToDto(book);
    }

    @Test
    void update_shouldReturnUpdatedBookDto() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        BookDto updatedDto = new BookDto(book.getId().toString(), "Updated Title", "Updated Author", false);
        BookDto result = bookService.update(book.getId().toString(), updatedDto);

        assertNotNull(result);
        assertEquals("Updated Author", book.getAuthor());
        assertEquals("Updated Title", book.getTitle());
        assertFalse(book.isAvailable());
        verify(bookRepository).save(book);
        verify(mapper).bookToDto(book);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        bookService.delete(book.getId().toString());

        verify(bookRepository).deleteById(book.getId());
    }
}