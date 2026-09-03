package com.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseRegistrationTest {

    @Test
    void eligibleStudent() {
        Course[] courses = {
            new Course("DS", 4),
            new Course("OS", 4),
            new Course("DBMS", 3),
            new Course("AI", 4)
        };

        assertEquals(15, CourseRegistration.calculateCredits(courses));
        assertTrue(CourseRegistration.calculateCredits(courses) >= 15);
    }

    @Test
    void notEligibleStudent() {
        Course[] courses = {
            new Course("DS", 4),
            new Course("OS", 3),
            new Course("DBMS", 3)
        };

        assertEquals(10, CourseRegistration.calculateCredits(courses));
        assertFalse(CourseRegistration.calculateCredits(courses) >= 15);
    }
}