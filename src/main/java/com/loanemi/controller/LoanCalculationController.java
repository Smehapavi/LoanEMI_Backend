package com.loanemi.controller;

import com.loanemi.dto.LoanRequest;
import com.loanemi.dto.LoanResponse;
import com.loanemi.service.LoanCalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanCalculationController {
    
    @Autowired
    private LoanCalculationService loanCalculationService;
    
    /**
     * Calculate EMI
     */
    @PostMapping("/calculate")
    public ResponseEntity<LoanResponse> calculateEmi(@Valid @RequestBody LoanRequest request) {
        try {
            LoanResponse response = loanCalculationService.calculateEmi(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    
    /**
     * Get all calculations
     */
    @GetMapping("/calculations")
    public ResponseEntity<List<LoanResponse>> getAllCalculations() {
        try {
            List<LoanResponse> calculations = loanCalculationService.getAllCalculations();
            return ResponseEntity.ok(calculations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    
    /**
     * Get calculation by ID
     */
    @GetMapping("/calculations/{id}")
    public ResponseEntity<LoanResponse> getCalculationById(@PathVariable Long id) {
        try {
            LoanResponse calculation = loanCalculationService.getCalculationById(id);
            return ResponseEntity.ok(calculation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    
    /**
     * Delete calculation by ID
     */
    @DeleteMapping("/calculations/{id}")
    public ResponseEntity<Void> deleteCalculation(@PathVariable Long id) {
        try {
            loanCalculationService.deleteCalculation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Loan EMI Calculator API is running!");
    }
} 