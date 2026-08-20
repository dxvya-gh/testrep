import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletSecurityQA {

    @BeforeEach
    void reset() {
        DigitalWallet.reset();
    }

    @Test
    void accountCreation() {
        DigitalWallet.createAccount("A100", "1234");

        assertEquals(
                0,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    @Test
    void deposit() {
        DigitalWallet.createAccount("A100", "1234");

        DigitalWallet.deposit("A100", 5000);

        assertEquals(
                5000,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    @Test
    void withdrawal() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 5000);

        DigitalWallet.withdraw(
                "A100", "1234", 1000, 1);

        assertEquals(
                4000,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    @Test
    void moneyTransfer() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.createAccount("A200", "5678");

        DigitalWallet.deposit("A100", 5000);

        DigitalWallet.transfer(
                "A100",
                "1234",
                "A200",
                2000,
                1);

        assertEquals(
                3000,
                DigitalWallet.getBalance("A100"),
                0.01);

        assertEquals(
                2000,
                DigitalWallet.getBalance("A200"),
                0.01);
    }

    @Test
    void insufficientBalance() {
        DigitalWallet.createAccount("A100", "1234");

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        1000,
                        1));
    }

    @Test
    void negativeAmount() {
        DigitalWallet.createAccount("A100", "1234");

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.deposit(
                        "A100",
                        -100));
    }

    @Test
    void zeroAmount() {
        DigitalWallet.createAccount("A100", "1234");

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.deposit(
                        "A100",
                        0));
    }

    @Test
    void invalidPin() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 5000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "9999",
                        1000,
                        1));
    }

    @Test
    void multipleFailedPins() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 5000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100", "9999", 100, 1));

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100", "9999", 100, 2));

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100", "9999", 100, 3));
    }

    @Test
    void dailyTransactionLimit() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 10000);

        // First ₹5,000
        DigitalWallet.withdraw(
                "A100",
                "1234",
                5000,
                1);

        // Second ₹5,000 reaches exactly ₹10,000
        DigitalWallet.withdraw(
                "A100",
                "1234",
                5000,
                2);

        // ₹1 more exceeds the daily limit
        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        1,
                        3));
    }

    @Test
    void largeTransactionFraud() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 10000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        6000,
                        1));
    }

    @Test
    void suspiciousTransactionFrequency() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 10000);

        // Five transactions are allowed
        for (int i = 1; i <= 5; i++) {
            DigitalWallet.withdraw(
                    "A100",
                    "1234",
                    100,
                    i);
        }

        // Sixth transaction within 10 minutes
        // should be flagged as suspicious
        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        100,
                        6));
    }

    @Test
    void transactionHistory() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.deposit("A100", 5000);

        DigitalWallet.withdraw(
                "A100",
                "1234",
                500,
                1);

        assertEquals(
                2,
                DigitalWallet.getTransactionHistory(
                        "A100").size());
    }

    @Test
    void duplicateAccount() {
        DigitalWallet.createAccount("A100", "1234");

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.createAccount(
                        "A100",
                        "5678"));
    }

    @Test
    void invalidAccount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.getBalance(
                        "UNKNOWN"));
    }

    @Test
    void invalidPinFormat() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.createAccount(
                        "A100",
                        "12"));
    }

    @Test
    void transferInsufficientBalance() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.createAccount("A200", "5678");

        DigitalWallet.deposit("A100", 500);

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.transfer(
                        "A100",
                        "1234",
                        "A200",
                        1000,
                        1));
    }

    @Test
    void transferHistory() {
        DigitalWallet.createAccount("A100", "1234");
        DigitalWallet.createAccount("A200", "5678");

        DigitalWallet.deposit("A100", 5000);

        DigitalWallet.transfer(
                "A100",
                "1234",
                "A200",
                1000,
                1);

        assertTrue(
                DigitalWallet
                        .getTransactionHistory("A100")
                        .get(1)
                        .contains("TRANSFER"));
    }
}