package com.loanemi.service;

import com.loanemi.dto.LoanRequest;
import com.loanemi.dto.LoanResponse;
import com.loanemi.model.LoanCalculation;
import com.loanemi.repository.LoanCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanCalculationService {
    
    @Autowired
    private LoanCalculationRepository loanCalculationRepository;
    
    /**
     * Calculate EMI and save the calculation
     */
    public LoanResponse calculateEmi(LoanRequest request) {
        // Calculate EMI using the formula: EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)
        // Where P = Principal, r = monthly interest rate, n = number of months
        
        BigDecimal principal = request.getPrincipalAmount();
        BigDecimal annualInterestRate = request.getInterestRate();
        int tenureMonths = request.getTenureMonths();
        
        // Convert annual interest rate to monthly rate (divide by 12 and 100)
        BigDecimal monthlyInterestRate = annualInterestRate
            .divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        
        // Calculate (1 + r)^n
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyInterestRate);
        BigDecimal onePlusRToN = onePlusR.pow(tenureMonths);
        
        // Calculate EMI
        BigDecimal numerator = principal.multiply(monthlyInterestRate).multiply(onePlusRToN);
        BigDecimal denominator = onePlusRToN.subtract(BigDecimal.ONE);
        BigDecimal monthlyEmi = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        
        // Calculate total amount and interest
        BigDecimal totalAmount = monthlyEmi.multiply(BigDecimal.valueOf(tenureMonths));
        BigDecimal totalInterest = totalAmount.subtract(principal);
        
        // Create and save the calculation
        LoanCalculation calculation = new LoanCalculation(
            principal, annualInterestRate, tenureMonths, 
            monthlyEmi, totalAmount, totalInterest
        );
        
        LoanCalculation savedCalculation = loanCalculationRepository.save(calculation);
        
        // Convert to response DTO
        return convertToResponse(savedCalculation);
    }
    
    /**
     * Get all loan calculations
     */
    public List<LoanResponse> getAllCalculations() {
        List<LoanCalculation> calculations = loanCalculationRepository.findAllByOrderByCalculatedAtDesc();
        return calculations.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Get calculation by ID
     */
    public LoanResponse getCalculationById(Long id) {
        LoanCalculation calculation = loanCalculationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Calculation not found with id: " + id));
        return convertToResponse(calculation);
    }
    
    /**
     * Delete calculation by ID
     */
    public void deleteCalculation(Long id) {
        if (!loanCalculationRepository.existsById(id)) {
            throw new RuntimeException("Calculation not found with id: " + id);
        }
        loanCalculationRepository.deleteById(id);
    }
    
    /**
     * Convert entity to response DTO
     */
    private LoanResponse convertToResponse(LoanCalculation calculation) {
        return new LoanResponse(
            calculation.getId(),
            calculation.getPrincipalAmount(),
            calculation.getInterestRate(),
            calculation.getTenureMonths(),
            calculation.getMonthlyEmi(),
            calculation.getTotalAmount(),
            calculation.getTotalInterest(),
            calculation.getCalculatedAt()
        );
    }
} 