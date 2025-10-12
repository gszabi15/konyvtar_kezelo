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
import org.gszabi15.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final EntityMapper mapper;

    public LoanDto borrowBook(String userId, String bookId, int days) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotAvailableException("Book not found with id: " + bookId));

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

    public List<LoanDto> getExpiredLoans() {
        List<Loan> list = loanRepo.findByDueDateBeforeAndReturnedFalse(LocalDate.now());
        return list.stream().map(mapper::loanToDto).toList();
    }

    public LoanDto returnLoan(Long loanId) {
        Loan loan = loanRepo.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));
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
