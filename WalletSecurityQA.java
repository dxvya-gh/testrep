import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletSecurityQA {

    @BeforeEach
    void reset() {
        DigitalWallet.reset();
    }

    // 1. Account creation
    @Test
    void accountCreation() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        assertEquals(
                0,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    // 2. Deposit
    @Test
    void deposit() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                5000);

        assertEquals(
                5000,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    // 3. Withdrawal
    @Test
    void withdrawal() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                5000);

        DigitalWallet.withdraw(
                "A100",
                "1234",
                1000,
                1);

        assertEquals(
                4000,
                DigitalWallet.getBalance("A100"),
                0.01);
    }

    // 4. Money transfer
    @Test
    void moneyTransfer() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.createAccount(
                "A200",
                "5678");

        DigitalWallet.deposit(
                "A100",
                5000);

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

    // 5. Insufficient balance
    @Test
    void insufficientBalance() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        1000,
                        1)
        );
    }

    // 6. Negative amount
    @Test
    void negativeAmount() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.deposit(
                        "A100",
                        -100)
        );
    }

    // 7. Zero amount
    @Test
    void zeroAmount() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.deposit(
                        "A100",
                        0)
        );
    }

    // 8. Invalid PIN
    @Test
    void invalidPin() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                5000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "9999",
                        1000,
                        1)
        );
    }

    // 9. Multiple failed PIN attempts
    @Test
    void multipleFailedPins() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                5000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "9999",
                        100,
                        1)
        );

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "9999",
                        100,
                        2)
        );

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "9999",
                        100,
                        3)
        );
    }

    // 10. Daily transaction limit
    @Test
    void dailyTransactionLimit() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                15000);

        DigitalWallet.withdraw(
                "A100",
                "1234",
                9000,
                1);

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        2000,
                        2)
        );
    }

    // 11. Large transaction fraud detection
    @Test
    void largeTransactionFraud() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                10000);

        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        6000,
                        1)
        );
    }

    // 12. Suspicious transaction frequency
    @Test
    void suspiciousTransactionFrequency() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                10000);

        // Four transactions are allowed.
        for (int i = 1; i <= 4; i++) {
            DigitalWallet.withdraw(
                    "A100",
                    "1234",
                    100,
                    i);
        }

        // Fifth existing transaction + new transaction
        // triggers the frequency rule.
        assertThrows(
                SecurityException.class,
                () -> DigitalWallet.withdraw(
                        "A100",
                        "1234",
                        100,
                        5)
        );
    }

    // 13. Transaction history
    @Test
    void transactionHistory() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.deposit(
                "A100",
                5000);

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

    // 14. Duplicate account
    @Test
    void duplicateAccount() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.createAccount(
                        "A100",
                        "5678")
        );
    }

    // 15. Invalid account
    @Test
    void invalidAccount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.getBalance(
                        "UNKNOWN")
        );
    }

    // 16. Invalid PIN format
    @Test
    void invalidPinFormat() {

        assertThrows(
                IllegalArgumentException.class,
                () -> DigitalWallet.createAccount(
                        "A100",
                        "12")
        );
    }

    // 17. Transfer insufficient balance
    @Test
    void transferInsufficientBalance() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.createAccount(
                "A200",
                "5678");

        DigitalWallet.deposit(
                "A100",
                500);

        assertThrows(
                IllegalStateException.class,
                () -> DigitalWallet.transfer(
                        "A100",
                        "1234",
                        "A200",
                        1000,
                        1)
        );
    }

    // 18. Normal transfer history
    @Test
    void transferHistory() {

        DigitalWallet.createAccount(
                "A100",
                "1234");

        DigitalWallet.createAccount(
                "A200",
                "5678");

        DigitalWallet.deposit(
                "A100",
                5000);

        DigitalWallet.transfer(
                "A100",
                "1234",
                "A200",
                1000,
                1);

        assertTrue(
                DigitalWallet.getTransactionHistory(
                        "A100")
                        .get(1)
                        .contains("TRANSFER"));
    }
}