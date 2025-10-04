package org.gszabi15.mapper;

import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto bookToBookDto(Book book);
    Book bookDtoToBook(BookDto bookDto);

    List<BookDto> bookListToBookDtoList(List<Book> books);
    List<Book> bookDtoListToBookList(List<BookDto> books);
}
