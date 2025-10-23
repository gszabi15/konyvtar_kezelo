package org.gszabi15.controller;

import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.model.entity.BorrowRequest;
import org.gszabi15.service.LoanService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanRestController {
    private final LoanService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/borrow")
    public LoanDto borrowAdmin(@RequestBody BorrowRequest request) {
        return service.borrowBook(request.getUserEmail(), request.getBookId().toString(), request.getDays());
    }

    @PostMapping("/borrow")
    public LoanDto borrow(@RequestBody BorrowRequest request) {
        return service.borrowBook(request.getBookId().toString(), request.getDays());
    }

    @PostMapping("/{loanId}/return")
    public LoanDto returnLoan(@PathVariable("loanId") String loanId) {
            return service.returnLoanByUser(loanId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/{loanId}/return")
    public LoanDto returnLoanAdmin(@PathVariable("loanId") String loanId) {
        return service.returnLoan(loanId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/expired")
    public List<LoanDto> expiredAdmin() {
        return service.getExpiredLoans();
    }

    @GetMapping("/expired")
    public List<LoanDto> expired() {
        return service.getExpiredLoansByUser();
    }
}
