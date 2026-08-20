import java.util.*;

public class ParkingManagement {

    enum VehicleType {
        BIKE, CAR, SUV, TRUCK, EV
    }

    static class Vehicle {
        String number;
        VehicleType type;

        Vehicle(String number, VehicleType type) {
            this.number = number;
            this.type = type;
        }
    }

    static class ParkingSlot {
        int slotNumber;
        VehicleType type;
        boolean occupied;
        String vehicleNumber;

        ParkingSlot(int slotNumber, VehicleType type) {
            this.slotNumber = slotNumber;
            this.type = type;
            this.occupied = false;
        }
    }

    static class ParkingTicket {
        String vehicleNumber;
        int slotNumber;
        long entryTime;
        VehicleType vehicleType;

        ParkingTicket(String vehicleNumber, int slotNumber,
                      long entryTime, VehicleType vehicleType) {
            this.vehicleNumber = vehicleNumber;
            this.slotNumber = slotNumber;
            this.entryTime = entryTime;
            this.vehicleType = vehicleType;
        }
    }

    static List<ParkingSlot> slots = new ArrayList<>();
    static Map<String, ParkingTicket> activeTickets = new HashMap<>();

    static {
        slots.add(new ParkingSlot(1, VehicleType.BIKE));
        slots.add(new ParkingSlot(2, VehicleType.CAR));
        slots.add(new ParkingSlot(3, VehicleType.SUV));
        slots.add(new ParkingSlot(4, VehicleType.TRUCK));
        slots.add(new ParkingSlot(5, VehicleType.EV));
    }

    public static int enterVehicle(
            Vehicle vehicle,
            long entryTime) {

        if (vehicle == null ||
                vehicle.number == null ||
                vehicle.number.isEmpty()) {
            throw new IllegalArgumentException("Invalid vehicle");
        }

        if (activeTickets.containsKey(vehicle.number)) {
            throw new IllegalStateException("Duplicate vehicle");
        }

        ParkingSlot selectedSlot = null;

        // EV gets EV slot; other vehicles get matching slot.
        for (ParkingSlot slot : slots) {
            if (!slot.occupied &&
                    slot.type == vehicle.type) {
                selectedSlot = slot;
                break;
            }
        }

        if (selectedSlot == null) {
            throw new IllegalStateException(
                    "No suitable parking slot");
        }

        selectedSlot.occupied = true;
        selectedSlot.vehicleNumber = vehicle.number;

        ParkingTicket ticket = new ParkingTicket(
                vehicle.number,
                selectedSlot.slotNumber,
                entryTime,
                vehicle.type
        );

        activeTickets.put(vehicle.number, ticket);

        return selectedSlot.slotNumber;
    }

    public static double exitVehicle(
            String vehicleNumber,
            long exitTime,
            boolean lostTicket,
            boolean vip,
            boolean peakHour) {

        if (!activeTickets.containsKey(vehicleNumber)) {
            throw new IllegalArgumentException(
                    "Vehicle not found");
        }

        ParkingTicket ticket =
                activeTickets.get(vehicleNumber);

        if (exitTime < ticket.entryTime) {
            throw new IllegalArgumentException(
                    "Invalid exit time");
        }

        double fee;

        if (lostTicket) {
            fee = 1000;
        } else {

            long duration =
                    exitTime - ticket.entryTime;

            // Time is represented in hours.
            double hours = Math.max(1, duration);

            double rate;

            switch (ticket.vehicleType) {
                case BIKE:
                    rate = 20;
                    break;

                case CAR:
                    rate = 50;
                    break;

                case SUV:
                    rate = 70;
                    break;

                case TRUCK:
                    rate = 100;
                    break;

                case EV:
                    rate = 60;
                    break;

                default:
                    rate = 50;
            }

            fee = hours * rate;

            // Peak-hour surcharge
            if (peakHour) {
                fee *= 1.50;
            }

            // VIP gets 20% discount
            if (vip) {
                fee *= 0.80;
            }

            // EV charging fee
            if (ticket.vehicleType == VehicleType.EV) {
                fee += 100;
            }
        }

        // Free the slot
        for (ParkingSlot slot : slots) {
            if (slot.slotNumber == ticket.slotNumber) {
                slot.occupied = false;
                slot.vehicleNumber = null;
                break;
            }
        }

        activeTickets.remove(vehicleNumber);

        return fee;
    }

    public static boolean isSlotOccupied(int slotNumber) {

        for (ParkingSlot slot : slots) {
            if (slot.slotNumber == slotNumber) {
                return slot.occupied;
            }
        }

        return false;
    }

    public static void resetParking() {

        activeTickets.clear();

        for (ParkingSlot slot : slots) {
            slot.occupied = false;
            slot.vehicleNumber = null;
        }
    }

    public static void main(String[] args) {

        resetParking();

        Vehicle car =
                new Vehicle("KA01AB1234", VehicleType.CAR);

        int slot = enterVehicle(car, 10);

        System.out.println(
                "Car parked in slot: " + slot);

        double fee =
                exitVehicle(
                        "KA01AB1234",
                        12,
                        false,
                        false,
                        false);

        System.out.printf(
                "Parking Fee: %.2f%n", fee);
    }
}