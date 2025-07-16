package com.example.LoanEMI.services;

import com.example.LoanEMI.models.Loan;
import com.example.LoanEMI.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        calculateEmi(loan);
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, Loan updatedLoan) {
        Loan existingLoan = loanRepository.findById(id).orElseThrow();
        existingLoan.setLoanAmount(updatedLoan.getLoanAmount());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setTenureMonths(updatedLoan.getTenureMonths());
        existingLoan.setUserId(updatedLoan.getUserId());
        calculateEmi(existingLoan);
        return loanRepository.save(existingLoan);
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElseThrow();
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    private void calculateEmi(Loan loan) {
        double P = loan.getLoanAmount();
        double R = loan.getInterestRate() / 12 / 100;
        int N = loan.getTenureMonths();

        double emi = (P * R * Math.pow(1 + R, N)) / (Math.pow(1 + R, N) - 1);
        double totalPayment = emi * N;
        double interest = totalPayment - P;

        loan.setMonthlyEmi(emi);
        loan.setTotalPayment(totalPayment);
        loan.setTotalInterest(interest);
    }
}
