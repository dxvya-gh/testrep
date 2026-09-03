package com.course;

import java.util.Scanner;

public class CourseRegistration {

    public static int calculateCredits(Course[] courses) {
        int total = 0;
        for (Course course : courses)
            total += course.credits;
        return total;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter student ID: ");
        String id = sc.nextLine();

        System.out.print("Enter number of subjects: ");
        int n = sc.nextInt();
        sc.nextLine();

        Course[] courses = new Course[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter subject name: ");
            String subject = sc.nextLine();

            System.out.print("Enter credits: ");
            int credits = sc.nextInt();
            sc.nextLine();

            courses[i] = new Course(subject, credits);
        }

        int total = calculateCredits(courses);

        System.out.println("\nStudent Name: " + name);
        System.out.println("Student ID: " + id);
        System.out.println("Registered Subjects:");

        for (Course course : courses)
            System.out.println(course.subject + " - " + course.credits + " credits");

        System.out.println("Total Credits: " + total);
        System.out.println(total >= 15 ? "Eligible" : "Not Eligible");

        sc.close();
    }
}