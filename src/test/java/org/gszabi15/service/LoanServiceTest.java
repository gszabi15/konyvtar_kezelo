package org.gszabi15.service;

import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.entity.Book;
import org.gszabi15.model.entity.Loan;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.repository.LoanRepository;
import org.gszabi15.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private LoanService loanService;

    private User user;
    private Book book;
    private Loan loan;
    private LoanDto loanDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@user.com");

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Test Title"); // title first
        book.setAuthor("Test Author"); // author second
        book.setAvailable(true);

        loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7));
        loan.setReturned(false);

        loanDto = new LoanDto();
        loanDto.setId(loan.getId().toString());
        loanDto.setBookId(book.getId().toString());
        loanDto.setUserId(user.getId().toString());
    }

    private void mockCurrentUser() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(user.getEmail());
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    void borrowBook_shouldReturnLoanDto() {
        mockCurrentUser();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(mapper.loanToDto(any(Loan.class))).thenReturn(loanDto);
        when(bookRepository.save(book)).thenReturn(book);

        LoanDto result = loanService.borrowBook(book.getId().toString(), 7);

        assertNotNull(result);
        assertEquals(book.getId().toString(), result.getBookId());
        assertFalse(book.isAvailable());
        verify(bookRepository).save(book);
        verify(loanRepository).save(any(Loan.class));
        verify(mapper).loanToDto(any(Loan.class));
    }

    @Test
    void getExpiredLoansByUser_shouldReturnList() {
        mockCurrentUser();
        when(loanRepository.findByDueDateBeforeAndReturnedFalse(LocalDate.now())).thenReturn(List.of(loan));
        when(mapper.loanToDto(loan)).thenReturn(loanDto);

        List<LoanDto> result = loanService.getExpiredLoansByUser();

        assertEquals(1, result.size());
        assertEquals(loan.getId().toString(), result.getFirst().getId());
        verify(mapper).loanToDto(loan);
    }

    @Test
    void getExpiredLoans_shouldReturnList() {
        when(loanRepository.findByDueDateBeforeAndReturnedFalse(LocalDate.now())).thenReturn(List.of(loan));
        when(mapper.loanToDto(loan)).thenReturn(loanDto);

        List<LoanDto> result = loanService.getExpiredLoans();

        assertEquals(1, result.size());
        assertEquals(loan.getId().toString(), result.getFirst().getId());
        verify(mapper).loanToDto(loan);
    }

    @Test
    void returnLoanByUser_shouldReturnLoanDto() {
        mockCurrentUser();
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        when(loanRepository.save(loan)).thenReturn(loan);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.loanToDto(loan)).thenReturn(loanDto);

        LoanDto result = loanService.returnLoanByUser(loan.getId().toString());

        assertNotNull(result);
        assertTrue(loan.isReturned());
        assertTrue(book.isAvailable());
        verify(loanRepository).save(loan);
        verify(bookRepository).save(book);
        verify(mapper).loanToDto(loan);
    }

    @Test
    void returnLoan_shouldReturnLoanDto() {
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        when(loanRepository.save(loan)).thenReturn(loan);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.loanToDto(loan)).thenReturn(loanDto);

        LoanDto result = loanService.returnLoan(loan.getId().toString());

        assertNotNull(result);
        assertTrue(loan.isReturned());
        assertTrue(book.isAvailable());
        verify(loanRepository).save(loan);
        verify(bookRepository).save(book);
        verify(mapper).loanToDto(loan);
    }
}