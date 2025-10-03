package org.gszabi15.config;

import org.gszabi15.model.Book;
import org.gszabi15.model.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto bookToBookDto(Book book);
    Book bookDtoToBook(BookDto bookDto);
}
