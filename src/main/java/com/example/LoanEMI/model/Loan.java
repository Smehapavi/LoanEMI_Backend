package com.example.LoanEMI.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double principal;
    private double rate;
    private int tenure; // in months

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
