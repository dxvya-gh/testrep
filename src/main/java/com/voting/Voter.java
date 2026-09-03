package com.voting;

public class Voter {
    String name;
    int age;
    String citizenship;
    String voterId;
    boolean idValid;

    public Voter(String name, int age, String citizenship, String voterId, boolean idValid) {
        this.name = name;
        this.age = age;
        this.citizenship = citizenship;
        this.voterId = voterId;
        this.idValid = idValid;
    }
}