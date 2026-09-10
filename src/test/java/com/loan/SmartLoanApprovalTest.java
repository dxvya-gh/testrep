package com.loan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartLoanApprovalTest {

    @Test
    void normalLowRiskCustomer() {
        Customer c = new Customer(
            "Div", 25, "GOV123", 60000, 780, 5000
        );

        LoanApplication app = new LoanApplication(c, 300000);

        assertEquals("Low Risk - Approved",
            CreditAssessment.assess(app));
    }

    @Test
    void minimumAgeBoundary() {
        Customer c = new Customer(
            "Rahul", 21, "GOV124", 50000, 700, 5000
        );

        LoanApplication app = new LoanApplication(c, 200000);

        assertFalse(CreditAssessment.assess(app)
            .contains("under 21"));
    }

    @Test
    void minimumCreditScoreBoundary() {
        Customer c = new Customer(
            "Priya", 25, "GOV125", 50000, 650, 5000
        );

        LoanApplication app = new LoanApplication(c, 200000);

        String result = CreditAssessment.assess(app);

        assertFalse(result.contains("Credit score is below minimum requirement"));
    }

    @Test
    void maximumDtiBoundary() {
        Customer c = new Customer(
            "Arun", 30, "GOV126", 50000, 700, 18000.0
        );

        LoanApplication app = new LoanApplication(c, 120000);

        double dti = CreditAssessment.calculateDTI(c, app.loanAmount);

        assertEquals(40.0, dti, 0.01);
        assertFalse(CreditAssessment.assess(app)
            .contains("DTI exceeds maximum allowed limit"));
    }

    @Test
    void maximumLoanCalculation() {
        Customer c = new Customer(
            "John", 30, "GOV127", 50000, 700, 5000
        );

        assertEquals(500000,
            CreditAssessment.calculateMaxLoan(c));
    }

    @Test
    void invalidCustomerInput() {
        Customer c = new Customer(
            "Sam", 20, "", -1000, 900, -500
        );

        assertThrows(
            LoanValidationException.class,
            () -> SmartLoanApproval.validateCustomer(c)
        );
    }

    @Test
    void multipleRejectionReasons() {
        Customer c = new Customer(
            "Alex", 19, "", 20000, 500, 15000
        );

        LoanApplication app = new LoanApplication(c, 500000);

        String result = CreditAssessment.assess(app);

        assertTrue(result.contains("Employee") == false);
        assertTrue(result.contains("under 21"));
        assertTrue(result.contains("Invalid government ID"));
        assertTrue(result.contains("Income is below minimum requirement"));
        assertTrue(result.contains("Requested loan exceeds maximum permissible amount"));
        assertTrue(result.contains("Credit score is below minimum requirement"));
        assertTrue(result.contains("DTI exceeds maximum allowed limit"));
    }
}