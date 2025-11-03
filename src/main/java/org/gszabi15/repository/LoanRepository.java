package org.gszabi15.repository;

import org.gszabi15.model.entity.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findByDueDateBeforeAndReturnedFalse(LocalDate date);
}
