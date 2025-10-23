package org.gszabi15.service;

import org.gszabi15.model.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BookServiceTest {
    @Autowired
    BookService service;

    BookDto testBook = new BookDto(null, "Test Author", "Test Description", true);

    
    @Test
    void getAllPaginated() throws Exception {
        service.getAllPaginated(0, 10);
    }

    @Test
    void getById() throws Exception {
        create();
        service.getById(testBook.getId());
    }

    @Test
    void create() throws Exception {
        testBook = service.create(testBook);
    }

    @Test
    void update() throws Exception {
        create();
        testBook.setAvailable(false);
        service.update(testBook.getId(), testBook);
    }

    @Test
    void delete() throws Exception {
        create();
        service.delete(testBook.getId());
    }
}