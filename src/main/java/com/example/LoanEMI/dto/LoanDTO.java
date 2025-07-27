package com.example.LoanEMI.dto;

import lombok.Data;

@Data
public class LoanDTO {
    private Long id;
    private double principal;
    private double rate;
    private int tenure; // in months
    private double emi;
    private double totalAmount;
    private double totalInterest;
}
