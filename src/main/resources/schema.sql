-- Create database if not exists
CREATE DATABASE IF NOT EXISTS loanemidb;
USE loanemidb;

-- Create loan_calculations table
CREATE TABLE IF NOT EXISTS loan_calculations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    principal_amount DECIMAL(15,2) NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    tenure_months INT NOT NULL,
    monthly_emi DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    total_interest DECIMAL(15,2) NOT NULL,
    calculated_at DATETIME NOT NULL
);

-- Create index for better performance
CREATE INDEX IF NOT EXISTS idx_calculated_at ON loan_calculations(calculated_at); 