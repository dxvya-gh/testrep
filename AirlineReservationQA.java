import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirlineReservationQA {

    // 1. Successful booking
    @Test
    void successfulBooking() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        15);

        assertEquals(4500, fare, 0.01);

        assertDoesNotThrow(() ->
                AirlineReservation.bookSeat(
                        "AI101", passenger));
    }


    // 2. Business class
    @Test
    void businessClass() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Business",
                        passenger,
                        30,
                        30);

        assertEquals(9000, fare, 0.01);
    }


    // 3. First class
    @Test
    void firstClass() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "First",
                        passenger,
                        30,
                        40);

        assertEquals(18000, fare, 0.01);
    }


    // 4. Double booking prevention
    @Test
    void doubleBookingPrevention() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "Alice", "Adult");

        assertDoesNotThrow(() ->
                AirlineReservation.bookSeat(
                        "AI202", passenger));
    }


    // 5. Cancellation and refund
    @Test
    void cancellationRefund() {

        double refund =
                AirlineReservation.cancelBooking(
                        5000, 30);

        assertEquals(4500, refund, 0.01);
    }


    // 6. Cancellation within 7 days
    @Test
    void lateCancellationRefund() {

        double refund =
                AirlineReservation.cancelBooking(
                        5000, 5);

        assertEquals(2500, refund, 0.01);
    }


    // 7. Invalid passenger
    @Test
    void invalidPassenger() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "", "Adult");

        assertThrows(
                IllegalArgumentException.class,
                () -> AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        15)
        );
    }


    // 8. Invalid flight
    @Test
    void invalidFlight() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        assertThrows(
                IllegalArgumentException.class,
                () -> AirlineReservation.calculateFare(
                        "INVALID",
                        "Economy",
                        passenger,
                        30,
                        15)
        );
    }


    // 9. Invalid class
    @Test
    void invalidClass() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        assertThrows(
                IllegalArgumentException.class,
                () -> AirlineReservation.calculateFare(
                        "AI101",
                        "Premium",
                        passenger,
                        30,
                        15)
        );
    }


    // 10. Excess baggage
    @Test
    void excessBaggage() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        20);

        // Base 5000 × 0.90 = 4500
        // 5kg excess × 500 = 2500
        assertEquals(7000, fare, 0.01);
    }


    // 11. Child fare
    @Test
    void childFare() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "Child", "Child");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        15);

        assertEquals(3375, fare, 0.01);
    }


    // 12. Senior fare
    @Test
    void seniorFare() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "Senior", "Senior");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        15);

        assertEquals(3825, fare, 0.01);
    }


    // 13. Last-minute booking
    @Test
    void lastMinuteBooking() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        double fare =
                AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        3,
                        15);

        assertEquals(6000, fare, 0.01);
    }


    // 14. Dynamic pricing
    @Test
    void dynamicPricing() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        AirlineReservation.Flight flight =
                AirlineReservation.flights.get("AI303");

        // Force availability into the 20% pricing band
        flight.availableSeats = 4;

        double fare =
                AirlineReservation.calculateFare(
                        "AI303",
                        "Economy",
                        passenger,
                        30,
                        15);

        assertEquals(6750, fare, 0.01);
    }


    // 15. Fully booked flight
    @Test
    void fullyBookedFlight() {

        AirlineReservation.Flight flight =
                AirlineReservation.flights.get("AI303");

        flight.availableSeats = 0;

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        assertThrows(
                IllegalStateException.class,
                () -> AirlineReservation.calculateFare(
                        "AI303",
                        "Economy",
                        passenger,
                        30,
                        15)
        );
    }


    // 16. Invalid baggage
    @Test
    void invalidBaggage() {

        AirlineReservation.Passenger passenger =
                new AirlineReservation.Passenger(
                        "John", "Adult");

        assertThrows(
                IllegalArgumentException.class,
                () -> AirlineReservation.calculateFare(
                        "AI101",
                        "Economy",
                        passenger,
                        30,
                        -5)
        );
    }


    // 17. Invalid fare cancellation
    @Test
    void invalidCancellationFare() {

        assertThrows(
                IllegalArgumentException.class,
                () -> AirlineReservation.cancelBooking(
                        -100,
                        30)
        );
    }


    // 18. Early cancellation
    @Test
    void earlyCancellation() {

        double refund =
                AirlineReservation.cancelBooking(
                        10000,
                        60);

        assertEquals(9000, refund, 0.01);
    }
}