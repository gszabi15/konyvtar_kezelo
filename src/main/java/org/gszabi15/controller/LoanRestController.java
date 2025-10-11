package org.gszabi15.controller;

import org.gszabi15.model.LoanDto;
import org.gszabi15.service.LoanService;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {
    private final LoanService service;

    public LoanRestController(LoanService service) {
        this.service = service;
    }

    @PostMapping("/borrow")
    public ResponseEntity<@NotNull LoanDto> borrow(@RequestParam String userId,
                                                   @RequestParam String bookId,
                                                   @RequestParam(defaultValue = "14") int days) {
        try {
            LoanDto dto = service.borrowBook(userId, bookId, days);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(null);
        }
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<@NotNull LoanDto> returnLoan(@PathVariable("loanId") Long loanId) {
        try {
            LoanDto dto = service.returnLoan(loanId);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/expired")
    public List<LoanDto> expired() {
        return service.getExpiredLoans();
    }
}
