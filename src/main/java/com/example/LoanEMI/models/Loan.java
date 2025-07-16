package com.example.LoanEMI.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double loanAmount;
    private Double interestRate;
    private Integer tenureMonths;

    private Double monthlyEmi;
    private Double totalInterest;
    private Double totalPayment;

    private Long userId;
}
