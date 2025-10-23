package org.gszabi15.service;

import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.entity.Book;
import org.gszabi15.model.entity.Loan;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.repository.LoanRepository;
import org.gszabi15.repository.UserRepository;
import org.gszabi15.exceptions.BookNotAvailableException;
import org.gszabi15.exceptions.LoanNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final EntityMapper mapper;

    public LoanDto borrowBook(String bookId, int days) {
        return borrowBook(getCurrentUser().getEmail(), bookId, days);
    }

    public LoanDto borrowBook(String userEmail, String bookId, int days) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        Book book = bookRepo.findById(UUID.fromString(bookId)).orElseThrow(() -> new BookNotAvailableException("Book not found with id: " + bookId));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book not available");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(days));
        loan.setReturned(false);

        book.setAvailable(false);
        bookRepo.save(book);

        Loan saved = loanRepo.save(loan);
        return mapper.loanToDto(saved);
    }

    public List<LoanDto> getExpiredLoansByUser() {
        List<Loan> list = loanRepo.findByDueDateBeforeAndReturnedFalse(LocalDate.now()).stream().filter(l -> l.getUser().getId().equals(getCurrentUser().getId())).toList();
        return list.stream().map(mapper::loanToDto).toList();
    }

    public List<LoanDto> getExpiredLoans() {
        List<Loan> list = loanRepo.findByDueDateBeforeAndReturnedFalse(LocalDate.now());
        return list.stream().map(mapper::loanToDto).toList();
    }

    public LoanDto returnLoanByUser(String loanId) {
        Loan loan = loanRepo.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));
        if (!loan.getUser().getId().equals(getCurrentUser().getId())) {
            throw new LoanNotFoundException("Loan not found with id: " + loanId);
        }
        return returnLoan(loanId);
    }

    public LoanDto returnLoan(String loanId) {
        Loan loan = loanRepo.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));
        if (!loan.isReturned()) {
            loan.setReturned(true);
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepo.save(book);
            loanRepo.save(loan);
        }
        return mapper.loanToDto(loan);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + auth.getName()));
    }
}
