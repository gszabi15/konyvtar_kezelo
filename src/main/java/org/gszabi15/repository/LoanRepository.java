package org.gszabi15.repository;

import org.gszabi15.model.entity.Loan;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<@NotNull Loan, @NotNull Long> {
    List<Loan> findByDueDateBeforeAndReturnedFalse(LocalDate date);
}
