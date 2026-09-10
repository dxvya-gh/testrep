package com.loan;

import java.util.Scanner;

public class SmartLoanApproval {

    public static void validateCustomer(Customer customer) throws LoanValidationException {
        if (customer.age <= 0)
            throw new LoanValidationException("Age must be positive");

        if (customer.governmentId == null || customer.governmentId.trim().isEmpty())
            throw new LoanValidationException("Government ID cannot be empty");

        if (customer.monthlyIncome <= 0)
            throw new LoanValidationException("Monthly income must be positive");

        if (customer.creditScore < 300 || customer.creditScore > 850)
            throw new LoanValidationException("Credit score must be between 300 and 850");

        if (customer.existingObligations < 0)
            throw new LoanValidationException("Existing obligations cannot be negative");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter number of customers: ");
            int n = Integer.parseInt(sc.nextLine());

            if (n <= 0)
                throw new LoanValidationException("Number of customers must be positive");

            for (int i = 1; i <= n; i++) {
                System.out.println("\nEnter details for customer " + i);

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Age: ");
                int age = Integer.parseInt(sc.nextLine());

                System.out.print("Government ID: ");
                String governmentId = sc.nextLine();

                System.out.print("Monthly income: ");
                double income = Double.parseDouble(sc.nextLine());

                System.out.print("Credit score: ");
                int creditScore = Integer.parseInt(sc.nextLine());

                System.out.print("Existing monthly loan obligations: ");
                double obligations = Double.parseDouble(sc.nextLine());

                System.out.print("Requested loan amount: ");
                double loanAmount = Double.parseDouble(sc.nextLine());

                Customer customer = new Customer(
                    name, age, governmentId, income,
                    creditScore, obligations
                );

                if (loanAmount <= 0)
                    throw new LoanValidationException("Loan amount must be positive");

                validateCustomer(customer);

                LoanApplication application =
                    new LoanApplication(customer, loanAmount);

                double maxLoan = CreditAssessment.calculateMaxLoan(customer);
                double dti = CreditAssessment.calculateDTI(customer, loanAmount);
                String result = CreditAssessment.assess(application);

                System.out.println("\n--- Loan Assessment ---");
                System.out.println("Customer: " + customer.name);
                System.out.println("Government ID: " + customer.governmentId);
                System.out.println("Maximum Permissible Loan: " + maxLoan);
                System.out.println("Requested Loan: " + loanAmount);
                System.out.println("DTI: " + String.format("%.2f", dti) + "%");
                System.out.println("Result: " + result);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter valid numbers.");
        } catch (LoanValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}