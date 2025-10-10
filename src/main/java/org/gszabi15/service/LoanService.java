package org.gszabi15.service;

import org.gszabi15.model.LoanDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.Book;
import org.gszabi15.model.Loan;
import org.gszabi15.model.User;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.repository.LoanRepository;
import org.gszabi15.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final EntityMapper mapper;

    public LoanService(LoanRepository loanRepo, UserRepository userRepo, BookRepository bookRepo, EntityMapper mapper) {
        this.loanRepo = loanRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.mapper = mapper;
    }

    public LoanDto borrowBook(String userId, String bookId, int days) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
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

    public List<LoanDto> getExpiredLoans() {
        List<Loan> list = loanRepo.findByDueDateBeforeAndReturnedFalse(LocalDate.now());
        return list.stream().map(mapper::loanToDto).toList();
    }

    public LoanDto returnLoan(Long loanId) {
        Loan loan = loanRepo.findById(loanId).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (!loan.isReturned()) {
            loan.setReturned(true);
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepo.save(book);
            loanRepo.save(loan);
        }
        return mapper.loanToDto(loan);
    }
}
