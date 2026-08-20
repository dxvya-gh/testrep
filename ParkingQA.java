import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingQA {

    @BeforeEach
    void reset() {
        ParkingManagement.resetParking();
    }

    // 1. Successful car parking
    @Test
    void successfulCarParking() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR001",
                        ParkingManagement.VehicleType.CAR);

        int slot =
                ParkingManagement.enterVehicle(car, 10);

        assertEquals(2, slot);
        assertTrue(
                ParkingManagement.isSlotOccupied(2));
    }

    // 2. Bike parking
    @Test
    void bikeParking() {

        ParkingManagement.Vehicle bike =
                new ParkingManagement.Vehicle(
                        "BIKE001",
                        ParkingManagement.VehicleType.BIKE);

        int slot =
                ParkingManagement.enterVehicle(bike, 10);

        assertEquals(1, slot);
    }

    // 3. SUV parking
    @Test
    void suvParking() {

        ParkingManagement.Vehicle suv =
                new ParkingManagement.Vehicle(
                        "SUV001",
                        ParkingManagement.VehicleType.SUV);

        int slot =
                ParkingManagement.enterVehicle(suv, 10);

        assertEquals(3, slot);
    }

    // 4. Truck parking
    @Test
    void truckParking() {

        ParkingManagement.Vehicle truck =
                new ParkingManagement.Vehicle(
                        "TRUCK001",
                        ParkingManagement.VehicleType.TRUCK);

        int slot =
                ParkingManagement.enterVehicle(truck, 10);

        assertEquals(4, slot);
    }

    // 5. EV parking
    @Test
    void electricVehicleParking() {

        ParkingManagement.Vehicle ev =
                new ParkingManagement.Vehicle(
                        "EV001",
                        ParkingManagement.VehicleType.EV);

        int slot =
                ParkingManagement.enterVehicle(ev, 10);

        assertEquals(5, slot);
    }

    // 6. Basic parking fee
    @Test
    void basicParkingFee() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR002",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "CAR002",
                        12,
                        false,
                        false,
                        false);

        // 2 hours × ₹50
        assertEquals(100, fee, 0.01);
    }

    // 7. Peak-hour pricing
    @Test
    void peakHourPricing() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR003",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "CAR003",
                        12,
                        false,
                        false,
                        true);

        // 100 × 1.5
        assertEquals(150, fee, 0.01);
    }

    // 8. VIP parking
    @Test
    void vipParking() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR004",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "CAR004",
                        12,
                        false,
                        true,
                        false);

        // 100 × 0.8
        assertEquals(80, fee, 0.01);
    }

    // 9. EV charging fee
    @Test
    void evChargingFee() {

        ParkingManagement.Vehicle ev =
                new ParkingManagement.Vehicle(
                        "EV002",
                        ParkingManagement.VehicleType.EV);

        ParkingManagement.enterVehicle(ev, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "EV002",
                        12,
                        false,
                        false,
                        false);

        // 2 × 60 + 100 charging
        assertEquals(220, fee, 0.01);
    }

    // 10. Lost ticket
    @Test
    void lostTicket() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR005",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "CAR005",
                        12,
                        true,
                        false,
                        false);

        assertEquals(1000, fee, 0.01);
    }

    // 11. Duplicate vehicle
    @Test
    void duplicateVehicle() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR006",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        assertThrows(
                IllegalStateException.class,
                () -> ParkingManagement.enterVehicle(
                        car, 12)
        );
    }

    // 12. Wrong vehicle/slot combination
    @Test
    void wrongVehicleSlotCombination() {

        // Only one CAR slot exists.
        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR007",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        // Second car cannot use the occupied CAR slot.
        ParkingManagement.Vehicle car2 =
                new ParkingManagement.Vehicle(
                        "CAR008",
                        ParkingManagement.VehicleType.CAR);

        assertThrows(
                IllegalStateException.class,
                () -> ParkingManagement.enterVehicle(
                        car2, 10)
        );
    }

    // 13. Full parking lot
    @Test
    void fullParkingLot() {

        ParkingManagement.enterVehicle(
                new ParkingManagement.Vehicle(
                        "BIKE002",
                        ParkingManagement.VehicleType.BIKE),
                10);

        ParkingManagement.enterVehicle(
                new ParkingManagement.Vehicle(
                        "CAR009",
                        ParkingManagement.VehicleType.CAR),
                10);

        ParkingManagement.enterVehicle(
                new ParkingManagement.Vehicle(
                        "SUV002",
                        ParkingManagement.VehicleType.SUV),
                10);

        ParkingManagement.enterVehicle(
                new ParkingManagement.Vehicle(
                        "TRUCK002",
                        ParkingManagement.VehicleType.TRUCK),
                10);

        ParkingManagement.enterVehicle(
                new ParkingManagement.Vehicle(
                        "EV003",
                        ParkingManagement.VehicleType.EV),
                10);

        assertThrows(
                IllegalStateException.class,
                () -> ParkingManagement.enterVehicle(
                        new ParkingManagement.Vehicle(
                                "CAR010",
                                ParkingManagement.VehicleType.CAR),
                        10)
        );
    }

    // 14. Early exit
    @Test
    void earlyExit() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR011",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> ParkingManagement.exitVehicle(
                        "CAR011",
                        9,
                        false,
                        false,
                        false)
        );
    }

    // 15. Vehicle not found
    @Test
    void vehicleNotFound() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ParkingManagement.exitVehicle(
                        "UNKNOWN",
                        12,
                        false,
                        false,
                        false)
        );
    }

    // 16. Invalid vehicle
    @Test
    void invalidVehicle() {

        ParkingManagement.Vehicle vehicle =
                new ParkingManagement.Vehicle(
                        "",
                        ParkingManagement.VehicleType.CAR);

        assertThrows(
                IllegalArgumentException.class,
                () -> ParkingManagement.enterVehicle(
                        vehicle, 10)
        );
    }

    // 17. Peak + VIP pricing
    @Test
    void peakAndVipPricing() {

        ParkingManagement.Vehicle car =
                new ParkingManagement.Vehicle(
                        "CAR012",
                        ParkingManagement.VehicleType.CAR);

        ParkingManagement.enterVehicle(car, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "CAR012",
                        12,
                        false,
                        true,
                        true);

        // 100 × 1.5 × 0.8 = 120
        assertEquals(120, fee, 0.01);
    }

    // 18. EV + peak pricing
    @Test
    void evPeakPricing() {

        ParkingManagement.Vehicle ev =
                new ParkingManagement.Vehicle(
                        "EV004",
                        ParkingManagement.VehicleType.EV);

        ParkingManagement.enterVehicle(ev, 10);

        double fee =
                ParkingManagement.exitVehicle(
                        "EV004",
                        12,
                        false,
                        false,
                        true);

        // 120 × 1.5 + 100
        assertEquals(280, fee, 0.01);
    }
}