package com.example.LoanEMI.dto;

import lombok.Data;

@Data
public class LoanDTO {
    private double principal;
    private double rate;
    private int tenure;
}
