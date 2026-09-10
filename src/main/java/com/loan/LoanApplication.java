package com.loan;

public class LoanApplication {
    Customer customer;
    double loanAmount;

    public LoanApplication(Customer customer, double loanAmount) {
        this.customer = customer;
        this.loanAmount = loanAmount;
    }
}