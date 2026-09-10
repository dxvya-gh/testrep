package com.loan;

import java.util.ArrayList;
import java.util.List;

public class CreditAssessment {

    public static final double MIN_INCOME = 30000;
    public static final int MIN_CREDIT_SCORE = 650;
    public static final double MAX_DTI = 40;
    public static final double LOW_RISK_DTI = 30;
    public static final int LOW_RISK_SCORE = 750;

    public static double calculateMaxLoan(Customer customer) {
        return customer.monthlyIncome * 10;
    }

    public static double calculateDTI(Customer customer, double loanAmount) {
        double newLoanMonthlyPayment = loanAmount / 60;
        return ((customer.existingObligations + newLoanMonthlyPayment)
                / customer.monthlyIncome) * 100;
    }

    public static String assess(LoanApplication application) {
        Customer customer = application.customer;
        List<String> reasons = new ArrayList<>();

        if (customer.age < 21)
            reasons.add("Customer is under 21");

        if (customer.governmentId == null || customer.governmentId.trim().isEmpty())
            reasons.add("Invalid government ID");

        if (customer.monthlyIncome < MIN_INCOME)
            reasons.add("Income is below minimum requirement");

        double maxLoan = calculateMaxLoan(customer);

        if (application.loanAmount > maxLoan)
            reasons.add("Requested loan exceeds maximum permissible amount");

        if (customer.creditScore < MIN_CREDIT_SCORE)
            reasons.add("Credit score is below minimum requirement");

        double dti = calculateDTI(customer, application.loanAmount);

        if (dti > MAX_DTI)
            reasons.add("DTI exceeds maximum allowed limit");

        if (!reasons.isEmpty())
            return "High Risk / Rejected\nReasons:\n- " + String.join("\n- ", reasons);

        if (customer.creditScore >= LOW_RISK_SCORE && dti <= LOW_RISK_DTI)
            return "Low Risk - Approved";

        return "Medium Risk - Approved";
    }
}