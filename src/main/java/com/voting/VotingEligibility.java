package com.voting;

import java.util.Scanner;

public class VotingEligibility {
    public static String checkEligibility(Voter voter) {
        if (voter.age < 18)
            return "Not eligible - Underage";
        if (!voter.citizenship.equalsIgnoreCase("Indian"))
            return "Not eligible - Not an Indian citizen";
        if (!voter.idValid)
            return "Not eligible - Invalid voter ID";
        return "Eligible to vote";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of voters: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for voter " + i + ":");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Citizenship: ");
            String citizenship = sc.nextLine();

            System.out.print("Voter ID: ");
            String voterId = sc.nextLine();

            System.out.print("Is voter ID valid (true/false): ");
            boolean idValid = sc.nextBoolean();
            sc.nextLine();

            Voter voter = new Voter(name, age, citizenship, voterId, idValid);

            System.out.println(name + ": " + checkEligibility(voter));
        }

        sc.close();
    }
}