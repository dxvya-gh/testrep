package com.voting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VotingEligibilityTest {

    @Test
    void eligibleVoter() {
        Voter voter = new Voter("Div", 20, "Indian", "ABC123", true);
        assertEquals("Eligible to vote", VotingEligibility.checkEligibility(voter));
    }

    @Test
    void underageVoter() {
        Voter voter = new Voter("Rahul", 17, "Indian", "ABC123", true);
        assertEquals("Not eligible - Underage", VotingEligibility.checkEligibility(voter));
    }

    @Test
    void nonCitizen() {
        Voter voter = new Voter("John", 25, "American", "ABC123", true);
        assertEquals("Not eligible - Not an Indian citizen", VotingEligibility.checkEligibility(voter));
    }

    @Test
    void invalidId() {
        Voter voter = new Voter("Priya", 25, "Indian", "ABC123", false);
        assertEquals("Not eligible - Invalid voter ID", VotingEligibility.checkEligibility(voter));
    }
}