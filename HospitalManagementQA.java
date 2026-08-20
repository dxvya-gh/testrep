import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalManagementQA {

    // 1. Regular general consultation
    @Test
    void regularGeneralConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        30,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(500, bill.consultationFee, 0.01);
        assertEquals(500, bill.patientPayable, 0.01);
    }


    // 2. Cardiology consultation
    @Test
    void cardiologyConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Kumar",
                        "Cardiology",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(1000, bill.consultationFee, 0.01);
    }


    // 3. Orthopedics consultation
    @Test
    void orthopedicsConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Rao",
                        "Orthopedics",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(800, bill.consultationFee, 0.01);
    }


    // 4. Pediatrics consultation
    @Test
    void pediatricsConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "Child",
                        10,
                        "Dr. Priya",
                        "Pediatrics",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(600, bill.consultationFee, 0.01);
    }


    // 5. Long consultation
    @Test
    void longConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        60,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        // 500 × 1.5
        assertEquals(750, bill.consultationFee, 0.01);
    }


    // 6. Emergency patient
    @Test
    void emergencyPatient() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "EMERGENCY",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        // 500 × 1.5
        assertEquals(750, bill.consultationFee, 0.01);
    }


    // 7. Senior citizen
    @Test
    void seniorCitizen() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "Senior",
                        65,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        // 500 × 90%
        assertEquals(450, bill.consultationFee, 0.01);
    }


    // 8. Follow-up consultation
    @Test
    void followUpConsultation() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "FOLLOW_UP",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        // 500 × 50%
        assertEquals(250, bill.consultationFee, 0.01);
    }


    // 9. Lab charges
    @Test
    void labCharges() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Arrays.asList(
                                new HospitalManagement.LabTest(
                                        "Blood Test", 500),
                                new HospitalManagement.LabTest(
                                        "X-Ray", 800)
                        ),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(1300, bill.labCharges, 0.01);
        assertEquals(1800, bill.totalAmount, 0.01);
    }


    // 10. Medicine charges
    @Test
    void medicineCharges() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Arrays.asList(
                                new HospitalManagement.Medicine(
                                        "Medicine A", 2, 100),
                                new HospitalManagement.Medicine(
                                        "Medicine B", 3, 50)
                        ),
                        new HospitalManagement.Insurance(false, 0)
                );

        // 2×100 + 3×50 = 350
        assertEquals(350, bill.medicineCharges, 0.01);
    }


    // 11. Insurance coverage
    @Test
    void insuranceCoverage() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(true, 80)
                );

        assertEquals(500, bill.totalAmount, 0.01);
        assertEquals(400, bill.insuranceCoverage, 0.01);
        assertEquals(100, bill.patientPayable, 0.01);
    }


    // 12. No insurance
    @Test
    void noInsurance() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(500, bill.totalAmount, 0.01);
        assertEquals(0, bill.insuranceCoverage, 0.01);
        assertEquals(500, bill.patientPayable, 0.01);
    }


    // 13. Emergency + lab + medicine
    @Test
    void emergencyCompleteBill() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "EMERGENCY",
                        30,
                        Arrays.asList(
                                new HospitalManagement.LabTest(
                                        "Blood Test", 500)
                        ),
                        Arrays.asList(
                                new HospitalManagement.Medicine(
                                        "Medicine", 2, 100)
                        ),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(750, bill.consultationFee, 0.01);
        assertEquals(500, bill.labCharges, 0.01);
        assertEquals(200, bill.medicineCharges, 0.01);
        assertEquals(1450, bill.totalAmount, 0.01);
    }


    // 14. Senior citizen + insurance
    @Test
    void seniorWithInsurance() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "Senior",
                        65,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(true, 80)
                );

        assertEquals(450, bill.consultationFee, 0.01);
        assertEquals(360, bill.insuranceCoverage, 0.01);
        assertEquals(90, bill.patientPayable, 0.01);
    }


    // 15. Follow-up with medicines
    @Test
    void followUpWithMedicine() {

        HospitalManagement.Bill bill =
                HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "FOLLOW_UP",
                        30,
                        Collections.emptyList(),
                        Arrays.asList(
                                new HospitalManagement.Medicine(
                                        "Medicine", 2, 100)
                        ),
                        new HospitalManagement.Insurance(false, 0)
                );

        assertEquals(250, bill.consultationFee, 0.01);
        assertEquals(200, bill.medicineCharges, 0.01);
        assertEquals(450, bill.patientPayable, 0.01);
    }


    // 16. Invalid department
    @Test
    void invalidDepartment() {

        assertThrows(
                IllegalArgumentException.class,
                () -> HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "InvalidDepartment",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(false, 0)
                )
        );
    }


    // 17. Negative medicine quantity
    @Test
    void negativeMedicineQuantity() {

        assertThrows(
                IllegalArgumentException.class,
                () -> HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Arrays.asList(
                                new HospitalManagement.Medicine(
                                        "Medicine", -1, 100)
                        ),
                        new HospitalManagement.Insurance(false, 0)
                )
        );
    }


    // 18. Invalid insurance percentage
    @Test
    void invalidInsuranceCoverage() {

        assertThrows(
                IllegalArgumentException.class,
                () -> HospitalManagement.calculateBill(
                        "John",
                        40,
                        "Dr. Smith",
                        "General",
                        "REGULAR",
                        30,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new HospitalManagement.Insurance(true, 120)
                )
        );
    }
}