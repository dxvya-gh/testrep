import java.util.*;

public class AirlineReservation {

    static class Flight {
        String flightNumber;
        int totalSeats;
        int availableSeats;

        Flight(String flightNumber, int totalSeats) {
            this.flightNumber = flightNumber;
            this.totalSeats = totalSeats;
            this.availableSeats = totalSeats;
        }
    }

    static class Passenger {
        String name;
        String passengerType;

        Passenger(String name, String passengerType) {
            this.name = name;
            this.passengerType = passengerType;
        }
    }

    static Map<String, Flight> flights = new HashMap<>();

    static {
        flights.put("AI101", new Flight("AI101", 100));
        flights.put("AI202", new Flight("AI202", 50));
        flights.put("AI303", new Flight("AI303", 20));
    }

    public static double calculateFare(
            String flightNumber,
            String travelClass,
            Passenger passenger,
            int bookingDaysBeforeTravel,
            int baggageKg) {

        if (!flights.containsKey(flightNumber)) {
            throw new IllegalArgumentException("Invalid flight");
        }

        if (passenger == null ||
                passenger.name == null ||
                passenger.name.isEmpty()) {
            throw new IllegalArgumentException("Invalid passenger");
        }

        if (baggageKg < 0) {
            throw new IllegalArgumentException("Invalid baggage");
        }

        Flight flight = flights.get(flightNumber);

        if (flight.availableSeats <= 0) {
            throw new IllegalStateException("Flight fully booked");
        }

        double baseFare;

        switch (travelClass) {
            case "Economy":
                baseFare = 5000;
                break;

            case "Business":
                baseFare = 10000;
                break;

            case "First":
                baseFare = 20000;
                break;

            default:
                throw new IllegalArgumentException("Invalid class");
        }

        // Dynamic pricing based on seat availability
        double availabilityMultiplier;

        if (flight.availableSeats <= flight.totalSeats * 0.20) {
            availabilityMultiplier = 1.50;
        } else if (flight.availableSeats <= flight.totalSeats * 0.50) {
            availabilityMultiplier = 1.25;
        } else {
            availabilityMultiplier = 1.00;
        }

        double fare = baseFare * availabilityMultiplier;

        // Earlier booking discount
        if (bookingDaysBeforeTravel >= 30) {
            fare *= 0.90;
        } else if (bookingDaysBeforeTravel < 7) {
            fare *= 1.20;
        }

        // Passenger type
        if (passenger.passengerType.equals("Child")) {
            fare *= 0.75;
        } else if (passenger.passengerType.equals("Senior")) {
            fare *= 0.85;
        }

        // Baggage charge
        int freeBaggage;

        if (travelClass.equals("Economy")) {
            freeBaggage = 15;
        } else if (travelClass.equals("Business")) {
            freeBaggage = 30;
        } else {
            freeBaggage = 40;
        }

        if (baggageKg > freeBaggage) {
            fare += (baggageKg - freeBaggage) * 500;
        }

        return fare;
    }

    public static void bookSeat(
            String flightNumber,
            Passenger passenger) {

        if (!flights.containsKey(flightNumber)) {
            throw new IllegalArgumentException("Invalid flight");
        }

        if (passenger == null ||
                passenger.name == null ||
                passenger.name.isEmpty()) {
            throw new IllegalArgumentException("Invalid passenger");
        }

        Flight flight = flights.get(flightNumber);

        if (flight.availableSeats <= 0) {
            throw new IllegalStateException("Flight fully booked");
        }

        flight.availableSeats--;
    }

    public static double cancelBooking(
            double fare,
            int daysBeforeTravel) {

        if (fare < 0) {
            throw new IllegalArgumentException("Invalid fare");
        }

        if (daysBeforeTravel < 0) {
            throw new IllegalArgumentException("Invalid cancellation date");
        }

        if (daysBeforeTravel >= 30) {
            return fare * 0.90;
        } else if (daysBeforeTravel >= 7) {
            return fare * 0.75;
        } else {
            return fare * 0.50;
        }
    }

    public static void main(String[] args) {

        Passenger passenger =
                new Passenger("John", "Adult");

        double fare = calculateFare(
                "AI101",
                "Economy",
                passenger,
                30,
                15
        );

        System.out.printf("Ticket Fare: %.2f%n", fare);

        bookSeat("AI101", passenger);

        System.out.println("Booking successful");
    }
}