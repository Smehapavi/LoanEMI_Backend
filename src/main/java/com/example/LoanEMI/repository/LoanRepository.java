package com.example.LoanEMI.repository;

import com.example.LoanEMI.model.Loan;
import com.example.LoanEMI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(User user);
}
