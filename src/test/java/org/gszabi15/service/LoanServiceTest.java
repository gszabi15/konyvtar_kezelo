package org.gszabi15.service;

import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@SpringBootTest
class LoanServiceTest {
    @Autowired
    LoanService service;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");
    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "ROLE_USER");
    private BookDto testbook = new BookDto(null, "testBook", "testAuthor", true);

    private LoanDto loan = new LoanDto();

    @BeforeEach
    void setUp() throws Exception {
        userService.create(testuser);
        testbook = bookService.create(testbook);

        Authentication auth = new UsernamePasswordAuthenticationToken(testuser.getEmail(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    void loginAdmin() {
        Authentication auth = new UsernamePasswordAuthenticationToken(adminuser.getEmail(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void borrowBook() throws Exception {
        loan = service.borrowBook(testbook.getId(), 14);
    }

    @Test
    void BorrowBookAdmin() throws Exception {
        loan = service.borrowBook(adminuser.getEmail(), testbook.getId(), 14);
    }

    @Test
    void getExpiredLoansByUser() throws Exception {
        service.getExpiredLoansByUser();
    }

    @Test
    void getExpiredLoans() throws Exception {
        loginAdmin();
        service.getExpiredLoans();
    }

    @Test
    void returnLoanByUser() throws Exception {
        borrowBook();
        service.returnLoanByUser(loan.getId());
    }

    @Test
    void returnLoan() throws Exception {
        borrowBook();
        loginAdmin();
        service.returnLoan(loan.getId());


    }
}