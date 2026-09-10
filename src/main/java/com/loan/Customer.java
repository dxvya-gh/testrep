package com.loan;

public class Customer {
    String name;
    int age;
    String governmentId;
    double monthlyIncome;
    int creditScore;
    double existingObligations;

    public Customer(String name, int age, String governmentId,
                    double monthlyIncome, int creditScore,
                    double existingObligations) {
        this.name = name;
        this.age = age;
        this.governmentId = governmentId;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
        this.existingObligations = existingObligations;
    }
}