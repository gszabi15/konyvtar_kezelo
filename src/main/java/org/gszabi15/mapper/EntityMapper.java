package org.gszabi15.mapper;

import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.entity.Book;
import org.gszabi15.model.entity.User;
import org.gszabi15.model.entity.Loan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {

    BookDto bookToDto(Book book);
    Book dtoToBook(BookDto dto);

    UserDto userToDto(User user);
    User dtoToUser(UserDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.id", target = "bookId")
    LoanDto loanToDto(Loan loan);
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "bookId", target = "book.id")
    Loan dtoToLoan(LoanDto dto);

    void updateUserFromDto(UserDto dto, @MappingTarget User entity);
    void updateBookFromDto(BookDto dto, @MappingTarget Book entity);
    void updateLoanFromDto(LoanDto dto, @MappingTarget Loan loan);

}