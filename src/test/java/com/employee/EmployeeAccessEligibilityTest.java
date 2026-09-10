package com.employee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAccessEligibilityTest {

    @Test
    void normalEligibleEmployee() {
        Employee e = new Employee("E101", "Div", 25, "IT", "Active", 4, true);

        AccessResult result = EmployeeAccessEligibility.checkEligibility(e, 3);

        assertEquals("Eligible", result.getStatus());
        assertTrue(result.getReasons().isEmpty());
    }

    @Test
    void boundaryAgeTwentyOne() {
        Employee e = new Employee("E102", "Rahul", 21, "HR", "Active", 3, true);

        AccessResult result = EmployeeAccessEligibility.checkEligibility(e, 2);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    void conditionallyEligibleEmployee() {
        Employee e = new Employee("E103", "Priya", 25, "Finance", "Active", 2, true);

        AccessResult result = EmployeeAccessEligibility.checkEligibility(e, 3);

        assertEquals("Conditionally Eligible", result.getStatus());
        assertTrue(result.getReasons().contains("Insufficient security clearance"));
    }

    @Test
    void invalidAccessLevel() {
        Employee e = new Employee("E104", "John", 25, "IT", "Active", 4, true);

        assertThrows(
            IllegalArgumentException.class,
            () -> EmployeeAccessEligibility.checkEligibility(e, 5)
        );
    }

    @Test
    void multipleFailures() {
        Employee e = new Employee("E105", "Sam", 19, "Sales", "Inactive", 1, false);

        AccessResult result = EmployeeAccessEligibility.checkEligibility(e, 4);

        assertEquals("Not Eligible", result.getStatus());
        assertEquals(5, result.getReasons().size());

        assertTrue(result.getReasons().contains("Employee is under 21"));
        assertTrue(result.getReasons().contains("Unauthorized department"));
        assertTrue(result.getReasons().contains("Employment is not active"));
        assertTrue(result.getReasons().contains("Invalid employee ID"));
        assertTrue(result.getReasons().contains("Insufficient security clearance"));
    }
}