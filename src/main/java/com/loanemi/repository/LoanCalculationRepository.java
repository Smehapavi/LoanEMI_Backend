package com.loanemi.repository;

import com.loanemi.model.LoanCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanCalculationRepository extends JpaRepository<LoanCalculation, Long> {
    
    // Find all calculations ordered by calculation date (newest first)
    List<LoanCalculation> findAllByOrderByCalculatedAtDesc();
    
    // Find calculations by principal amount range
    List<LoanCalculation> findByPrincipalAmountBetweenOrderByCalculatedAtDesc(
        java.math.BigDecimal minAmount, java.math.BigDecimal maxAmount);
} 