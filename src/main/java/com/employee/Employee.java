package com.employee;

public class Employee {
    String employeeId;
    String name;
    int age;
    String department;
    String employmentType;
    int securityClearance;
    boolean idValid;

    public Employee(String employeeId, String name, int age, String department,
                    String employmentType, int securityClearance, boolean idValid) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.employmentType = employmentType;
        this.securityClearance = securityClearance;
        this.idValid = idValid;
    }
}