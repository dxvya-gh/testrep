package com.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeAccessEligibility {

    public static AccessResult checkEligibility(Employee employee, int requestedLevel) {
        List<String> reasons = new ArrayList<>();

        if (employee.age < 21)
            reasons.add("Employee is under 21");

        if (!employee.department.equalsIgnoreCase("IT") &&
            !employee.department.equalsIgnoreCase("HR") &&
            !employee.department.equalsIgnoreCase("Finance") &&
            !employee.department.equalsIgnoreCase("Administration"))
            reasons.add("Unauthorized department");

        if (!employee.employmentType.equalsIgnoreCase("Active"))
            reasons.add("Employment is not active");

        if (!employee.idValid)
            reasons.add("Invalid employee ID");

        if (requestedLevel < 1 || requestedLevel > 4)
            throw new IllegalArgumentException("Invalid access level");

        if (employee.securityClearance < requestedLevel)
            reasons.add("Insufficient security clearance");

        if (reasons.isEmpty())
            return new AccessResult("Eligible", reasons);

        if (reasons.size() == 1 &&
            reasons.get(0).equals("Insufficient security clearance"))
            return new AccessResult("Conditionally Eligible", reasons);

        return new AccessResult("Not Eligible", reasons);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter number of employees: ");
            int n = Integer.parseInt(sc.nextLine());

            if (n <= 0)
                throw new IllegalArgumentException("Number of employees must be positive");

            for (int i = 1; i <= n; i++) {
                System.out.println("\nEnter details for employee " + i);

                System.out.print("Employee ID: ");
                String employeeId = sc.nextLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Age: ");
                int age = Integer.parseInt(sc.nextLine());

                System.out.print("Department: ");
                String department = sc.nextLine();

                System.out.print("Employment type (Active/Inactive): ");
                String employmentType = sc.nextLine();

                System.out.print("Security clearance (1-4): ");
                int securityClearance = Integer.parseInt(sc.nextLine());

                System.out.print("Is employee ID valid (true/false): ");
                boolean idValid = Boolean.parseBoolean(sc.nextLine());

                System.out.print("Requested access level (1-4): ");
                int requestedLevel = Integer.parseInt(sc.nextLine());

                Employee employee = new Employee(
                    employeeId, name, age, department,
                    employmentType, securityClearance, idValid
                );

                AccessResult result =
                    checkEligibility(employee, requestedLevel);

                System.out.println("\nEmployee: " + employee.name);
                System.out.println("Employee ID: " + employee.employeeId);
                System.out.println("Status: " + result.getStatus());

                if (!result.getReasons().isEmpty()) {
                    System.out.println("Reasons:");
                    for (String reason : result.getReasons())
                        System.out.println("- " + reason);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter numbers where required.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}