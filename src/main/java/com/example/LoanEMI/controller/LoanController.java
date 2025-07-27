package com.example.LoanEMI.controller;

import com.example.LoanEMI.dto.LoanDTO;
import com.example.LoanEMI.model.User;
import com.example.LoanEMI.service.LoanService;
import com.example.LoanEMI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {

    private final LoanService loanService;
    private final UserService userService;

    @PostMapping("/calculate")
    public LoanDTO calculateEMI(@RequestBody LoanDTO dto) {
        return loanService.calculateEMI(dto);
    }

    @PostMapping
    public String applyLoan(@RequestBody LoanDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        loanService.saveLoan(dto, user);
        return "Loan applied successfully";
    }

    @GetMapping
    public List<LoanDTO> getMyLoans() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        return loanService.getUserLoans(user);
    }

    @DeleteMapping("/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "Loan deleted successfully";
    }
}
