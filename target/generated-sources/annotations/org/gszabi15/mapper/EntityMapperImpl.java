package org.gszabi15.mapper;

import javax.annotation.processing.Generated;
import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.model.entity.Book;
import org.gszabi15.model.entity.Loan;
import org.gszabi15.model.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:59:58+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public BookDto bookToDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setAuthor( book.getAuthor() );
        bookDto.setAvailable( book.isAvailable() );

        return bookDto;
    }

    @Override
    public Book dtoToBook(BookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( dto.getId() );
        book.setTitle( dto.getTitle() );
        book.setAuthor( dto.getAuthor() );
        book.setAvailable( dto.isAvailable() );

        return book;
    }

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setEmail( user.getEmail() );

        return userDto;
    }

    @Override
    public User dtoToUser(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setName( dto.getName() );
        user.setEmail( dto.getEmail() );

        return user;
    }

    @Override
    public LoanDto loanToDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanDto loanDto = new LoanDto();

        loanDto.setUserId( loanUserId( loan ) );
        loanDto.setBookId( loanBookId( loan ) );
        loanDto.setId( loan.getId() );
        loanDto.setBorrowDate( loan.getBorrowDate() );
        loanDto.setDueDate( loan.getDueDate() );
        loanDto.setReturned( loan.isReturned() );

        return loanDto;
    }

    @Override
    public Loan dtoToLoan(LoanDto dto) {
        if ( dto == null ) {
            return null;
        }

        Loan loan = new Loan();

        loan.setUser( loanDtoToUser( dto ) );
        loan.setBook( loanDtoToBook( dto ) );
        loan.setId( dto.getId() );
        loan.setBorrowDate( dto.getBorrowDate() );
        loan.setDueDate( dto.getDueDate() );
        loan.setReturned( dto.isReturned() );

        return loan;
    }

    @Override
    public void updateUserFromDto(UserDto dto, User entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setEmail( dto.getEmail() );
    }

    @Override
    public void updateBookFromDto(BookDto dto, Book entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
        entity.setAuthor( dto.getAuthor() );
        entity.setAvailable( dto.isAvailable() );
    }

    @Override
    public void updateLoanFromDto(LoanDto dto, Loan loan) {
        if ( dto == null ) {
            return;
        }

        loan.setId( dto.getId() );
        loan.setBorrowDate( dto.getBorrowDate() );
        loan.setDueDate( dto.getDueDate() );
        loan.setReturned( dto.isReturned() );
    }

    private String loanUserId(Loan loan) {
        User user = loan.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String loanBookId(Loan loan) {
        Book book = loan.getBook();
        if ( book == null ) {
            return null;
        }
        return book.getId();
    }

    protected User loanDtoToUser(LoanDto loanDto) {
        if ( loanDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( loanDto.getUserId() );

        return user;
    }

    protected Book loanDtoToBook(LoanDto loanDto) {
        if ( loanDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( loanDto.getBookId() );

        return book;
    }
}
