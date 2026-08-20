import java.util.*;

public class HospitalManagement {

    static class LabTest {
        String name;
        double charge;

        LabTest(String name, double charge) {
            this.name = name;
            this.charge = charge;
        }
    }

    static class Medicine {
        String name;
        int quantity;
        double unitPrice;

        Medicine(String name, int quantity, double unitPrice) {
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }

    static class Insurance {
        boolean insured;
        double coveragePercent;

        Insurance(boolean insured, double coveragePercent) {
            this.insured = insured;
            this.coveragePercent = coveragePercent;
        }
    }

    static class Bill {
        double consultationFee;
        double labCharges;
        double medicineCharges;
        double totalAmount;
        double insuranceCoverage;
        double patientPayable;

        Bill(double consultationFee,
             double labCharges,
             double medicineCharges,
             double totalAmount,
             double insuranceCoverage,
             double patientPayable) {

            this.consultationFee = consultationFee;
            this.labCharges = labCharges;
            this.medicineCharges = medicineCharges;
            this.totalAmount = totalAmount;
            this.insuranceCoverage = insuranceCoverage;
            this.patientPayable = patientPayable;
        }
    }

    public static Bill calculateBill(
            String patientName,
            int age,
            String doctor,
            String department,
            String appointmentType,
            int consultationDuration,
            List<LabTest> labTests,
            List<Medicine> medicines,
            Insurance insurance) {

        // Base consultation fee based on department
        double consultationFee;

        switch (department) {
            case "General":
                consultationFee = 500;
                break;

            case "Cardiology":
                consultationFee = 1000;
                break;

            case "Orthopedics":
                consultationFee = 800;
                break;

            case "Pediatrics":
                consultationFee = 600;
                break;

            default:
                throw new IllegalArgumentException("Invalid department");
        }

        // Consultation duration surcharge
        if (consultationDuration > 30) {
            consultationFee *= 1.5;
        }

        // Emergency consultation
        if (appointmentType.equals("EMERGENCY")) {
            consultationFee *= 1.5;
        }

        // Follow-up consultation
        if (appointmentType.equals("FOLLOW_UP")) {
            consultationFee *= 0.5;
        }

        // Senior citizen discount
        if (age >= 60) {
            consultationFee *= 0.90;
        }

        // Lab charges
        double labCharges = 0;

        for (LabTest test : labTests) {
            labCharges += test.charge;
        }

        // Medicine charges
        double medicineCharges = 0;

        for (Medicine medicine : medicines) {

            if (medicine.quantity < 0) {
                throw new IllegalArgumentException(
                        "Invalid medicine quantity");
            }

            medicineCharges += medicine.quantity * medicine.unitPrice;
        }

        // Total before insurance
        double totalAmount =
                consultationFee + labCharges + medicineCharges;

        // Insurance
        double insuranceCoverage = 0;

        if (insurance != null && insurance.insured) {

            if (insurance.coveragePercent < 0 ||
                    insurance.coveragePercent > 100) {
                throw new IllegalArgumentException(
                        "Invalid insurance coverage");
            }

            insuranceCoverage =
                    totalAmount * insurance.coveragePercent / 100.0;
        }

        double patientPayable =
                totalAmount - insuranceCoverage;

        return new Bill(
                consultationFee,
                labCharges,
                medicineCharges,
                totalAmount,
                insuranceCoverage,
                patientPayable
        );
    }

    public static void main(String[] args) {

        List<LabTest> tests = Arrays.asList(
                new LabTest("Blood Test", 500),
                new LabTest("X-Ray", 800)
        );

        List<Medicine> medicines = Arrays.asList(
                new Medicine("Paracetamol", 2, 50),
                new Medicine("Antibiotic", 1, 200)
        );

        Insurance insurance =
                new Insurance(true, 80);

        Bill bill = calculateBill(
                "John",
                45,
                "Dr. Smith",
                "General",
                "REGULAR",
                30,
                tests,
                medicines,
                insurance
        );

        System.out.printf("Consultation Fee: %.2f%n",
                bill.consultationFee);

        System.out.printf("Lab Charges: %.2f%n",
                bill.labCharges);

        System.out.printf("Medicine Charges: %.2f%n",
                bill.medicineCharges);

        System.out.printf("Total Amount: %.2f%n",
                bill.totalAmount);

        System.out.printf("Insurance Coverage: %.2f%n",
                bill.insuranceCoverage);

        System.out.printf("Patient Payable: %.2f%n",
                bill.patientPayable);
    }
}