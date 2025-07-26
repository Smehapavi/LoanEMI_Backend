package com.example.LoanEMI.service;

import com.example.LoanEMI.dto.LoanDTO;
import com.example.LoanEMI.model.Loan;
import com.example.LoanEMI.model.User;
import com.example.LoanEMI.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepo;

    public Loan saveLoan(LoanDTO loanDTO, User user) {
        Loan loan = new Loan();
        loan.setPrincipal(loanDTO.getPrincipal());
        loan.setRate(loanDTO.getRate());
        loan.setTenure(loanDTO.getTenure());
        loan.setUser(user);
        return loanRepo.save(loan);
    }

    public List<Loan> getUserLoans(User user) {
        return loanRepo.findByUser(user);
    }

    public void deleteLoan(Long id) {
        loanRepo.deleteById(id);
    }
}
