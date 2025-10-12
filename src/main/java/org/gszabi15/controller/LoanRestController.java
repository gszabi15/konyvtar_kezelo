package org.gszabi15.controller;

import org.gszabi15.model.dto.LoanDto;
import org.gszabi15.service.LoanService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanRestController {
    private final LoanService service;

    @PostMapping("/borrow")
    public LoanDto borrow(@RequestParam String userId,
                                                   @RequestParam String bookId,
                                                   @RequestParam(defaultValue = "14") int days) {

        return service.borrowBook(userId, bookId, days);
    }

    @PostMapping("/{loanId}/return")
    public LoanDto returnLoan(@PathVariable("loanId") Long loanId) {
            return service.returnLoan(loanId);
    }

    @GetMapping("/expired")
    public List<LoanDto> expired() {
        return service.getExpiredLoans();
    }
}
