package org.gszabi15.controller;

import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.entity.BorrowRequest;
import org.gszabi15.service.LoanService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanRestControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanRestController loanRestController;

    private LoanDto loanDto;
    private BorrowRequest borrowRequest;

    @BeforeEach
    void setUp() {
        loanDto = new LoanDto();
        loanDto.setId(UUID.randomUUID().toString());
        loanDto.setBookId(UUID.randomUUID().toString());
        loanDto.setUserId(UUID.randomUUID().toString());

        borrowRequest = new BorrowRequest();
        borrowRequest.setBookId(UUID.randomUUID());
        borrowRequest.setUserEmail("test@t.com");
        borrowRequest.setDays(7);
    }

    @Test
    void borrowAdmin_shouldReturnLoanDto() {
        when(loanService.borrowBook(borrowRequest.getUserEmail(), borrowRequest.getBookId().toString(), 7))
                .thenReturn(loanDto);

        LoanDto result = loanRestController.borrowAdmin(borrowRequest);

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        verify(loanService).borrowBook(borrowRequest.getUserEmail(), borrowRequest.getBookId().toString(), 7);
    }

    @Test
    void borrow_shouldReturnLoanDto() {
        when(loanService.borrowBook(borrowRequest.getBookId().toString(), 7)).thenReturn(loanDto);

        LoanDto result = loanRestController.borrow(borrowRequest);

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        verify(loanService).borrowBook(borrowRequest.getBookId().toString(), 7);
    }

    @Test
    void returnLoan_shouldReturnLoanDto() {
        when(loanService.returnLoanByUser(loanDto.getId())).thenReturn(loanDto);

        LoanDto result = loanRestController.returnLoan(loanDto.getId());

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        verify(loanService).returnLoanByUser(loanDto.getId());
    }

    @Test
    void returnLoanAdmin_shouldReturnLoanDto() {
        when(loanService.returnLoan(loanDto.getId())).thenReturn(loanDto);

        LoanDto result = loanRestController.returnLoanAdmin(loanDto.getId());

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        verify(loanService).returnLoan(loanDto.getId());
    }

    @Test
    void expiredAdmin_shouldReturnList() {
        when(loanService.getExpiredLoans()).thenReturn(List.of(loanDto));

        List<LoanDto> result = loanRestController.expiredAdmin();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanService).getExpiredLoans();
    }

    @Test
    void expired_shouldReturnList() {
        when(loanService.getExpiredLoansByUser()).thenReturn(List.of(loanDto));

        List<LoanDto> result = loanRestController.expired();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanService).getExpiredLoansByUser();
    }
}