package com.example.LoanEMI.service;

import com.example.LoanEMI.dto.LoanDTO;
import com.example.LoanEMI.model.Loan;
import com.example.LoanEMI.model.User;
import com.example.LoanEMI.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepo;

    public LoanDTO calculateEMI(LoanDTO loanDTO) {
        double principal = loanDTO.getPrincipal();
        double annualRate = loanDTO.getRate();
        int tenure = loanDTO.getTenure();

        // Convert annual rate to monthly rate and percentage to decimal
        double monthlyRate = (annualRate / 100) / 12;
        
        // EMI calculation using the formula: EMI = P * r * (1+r)^n / ((1+r)^n - 1)
        double emi;
        if (monthlyRate == 0) {
            // If no interest, EMI is simply principal divided by tenure
            emi = principal / tenure;
        } else {
            double factor = Math.pow(1 + monthlyRate, tenure);
            emi = principal * monthlyRate * factor / (factor - 1);
        }
        
        double totalAmount = emi * tenure;
        double totalInterest = totalAmount - principal;

        loanDTO.setEmi(Math.round(emi * 100.0) / 100.0);
        loanDTO.setTotalAmount(Math.round(totalAmount * 100.0) / 100.0);
        loanDTO.setTotalInterest(Math.round(totalInterest * 100.0) / 100.0);

        return loanDTO;
    }

    public Loan saveLoan(LoanDTO loanDTO, User user) {
        // Calculate EMI before saving
        LoanDTO calculatedLoan = calculateEMI(loanDTO);
        
        Loan loan = new Loan();
        loan.setPrincipal(calculatedLoan.getPrincipal());
        loan.setRate(calculatedLoan.getRate());
        loan.setTenure(calculatedLoan.getTenure());
        loan.setEmi(calculatedLoan.getEmi());
        loan.setTotalAmount(calculatedLoan.getTotalAmount());
        loan.setTotalInterest(calculatedLoan.getTotalInterest());
        loan.setUser(user);
        return loanRepo.save(loan);
    }

    public List<LoanDTO> getUserLoans(User user) {
        return loanRepo.findByUser(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteLoan(Long id) {
        loanRepo.deleteById(id);
    }

    private LoanDTO mapToDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setPrincipal(loan.getPrincipal());
        dto.setRate(loan.getRate());
        dto.setTenure(loan.getTenure());
        dto.setEmi(loan.getEmi());
        dto.setTotalAmount(loan.getTotalAmount());
        dto.setTotalInterest(loan.getTotalInterest());
        return dto;
    }
}
