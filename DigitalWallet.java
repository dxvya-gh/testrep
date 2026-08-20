import java.util.*;

public class DigitalWallet {

    static class Account {
        String accountId;
        String pin;
        double balance;
        double dailyWithdrawn;
        List<Long> transactionTimes;
        List<String> transactionHistory;
        int failedPinAttempts;

        Account(String accountId, String pin) {
            this.accountId = accountId;
            this.pin = pin;
            this.balance = 0;
            this.dailyWithdrawn = 0;
            this.transactionTimes = new ArrayList<>();
            this.transactionHistory = new ArrayList<>();
            this.failedPinAttempts = 0;
        }
    }

    static final double DAILY_LIMIT = 10000;
    static final double LARGE_TRANSACTION_LIMIT = 5000;

    static Map<String, Account> accounts = new HashMap<>();

    public static void createAccount(
            String accountId,
            String pin) {

        if (accountId == null ||
                accountId.isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid account ID");
        }

        if (pin == null ||
                pin.length() != 4) {
            throw new IllegalArgumentException(
                    "Invalid PIN");
        }

        if (accounts.containsKey(accountId)) {
            throw new IllegalStateException(
                    "Account already exists");
        }

        accounts.put(
                accountId,
                new Account(accountId, pin));
    }

    public static void deposit(
            String accountId,
            double amount) {

        Account account = getAccount(accountId);

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Invalid amount");
        }

        account.balance += amount;

        account.transactionHistory.add(
                "DEPOSIT:" + amount);
    }

    public static void withdraw(
            String accountId,
            String pin,
            double amount,
            long timestamp) {

        Account account = getAccount(accountId);

        verifyPin(account, pin);

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Invalid amount");
        }

        if (amount > account.balance) {
            throw new IllegalStateException(
                    "Insufficient balance");
        }

        if (account.dailyWithdrawn + amount >
                DAILY_LIMIT) {
            throw new IllegalStateException(
                    "Daily transaction limit exceeded");
        }

        checkFraud(account, amount, timestamp);

        account.balance -= amount;
        account.dailyWithdrawn += amount;

        recordTransaction(
                account,
                "WITHDRAW:" + amount,
                timestamp);
    }

    public static void transfer(
            String fromAccount,
            String pin,
            String toAccount,
            double amount,
            long timestamp) {

        Account sender = getAccount(fromAccount);
        Account receiver = getAccount(toAccount);

        verifyPin(sender, pin);

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Invalid amount");
        }

        if (amount > sender.balance) {
            throw new IllegalStateException(
                    "Insufficient balance");
        }

        if (sender.dailyWithdrawn + amount >
                DAILY_LIMIT) {
            throw new IllegalStateException(
                    "Daily transaction limit exceeded");
        }

        checkFraud(sender, amount, timestamp);

        sender.balance -= amount;
        sender.dailyWithdrawn += amount;
        receiver.balance += amount;

        recordTransaction(
                sender,
                "TRANSFER:" + amount +
                        ":TO:" + toAccount,
                timestamp);

        receiver.transactionHistory.add(
                "TRANSFER_RECEIVED:" + amount +
                        ":FROM:" + fromAccount);
    }

    static void verifyPin(
            Account account,
            String pin) {

        if (!account.pin.equals(pin)) {

            account.failedPinAttempts++;

            if (account.failedPinAttempts >= 3) {
                throw new SecurityException(
                        "Account locked");
            }

            throw new SecurityException(
                    "Invalid PIN");
        }

        account.failedPinAttempts = 0;
    }

    static void checkFraud(
            Account account,
            double amount,
            long timestamp) {

        // More than 5 transactions in 10 minutes
        long tenMinutes = 10;

        int recentTransactions = 0;

        for (long time : account.transactionTimes) {
            if (timestamp - time <= tenMinutes) {
                recentTransactions++;
            }
        }

        if (recentTransactions >= 5) {
            throw new SecurityException(
                    "Suspicious transaction frequency");
        }

        // Large transaction
        if (amount > LARGE_TRANSACTION_LIMIT) {
            throw new SecurityException(
                    "Suspicious large transaction");
        }
    }

    static void recordTransaction(
            Account account,
            String description,
            long timestamp) {

        account.transactionTimes.add(timestamp);
        account.transactionHistory.add(description);
    }

    static Account getAccount(String accountId) {

        Account account = accounts.get(accountId);

        if (account == null) {
            throw new IllegalArgumentException(
                    "Account not found");
        }

        return account;
    }

    public static double getBalance(
            String accountId) {

        return getAccount(accountId).balance;
    }

    public static List<String> getTransactionHistory(
            String accountId) {

        return getAccount(accountId).transactionHistory;
    }

    public static void reset() {
        accounts.clear();
    }

    public static void main(String[] args) {

        reset();

        createAccount("A100", "1234");
        createAccount("A200", "5678");

        deposit("A100", 10000);

        transfer(
                "A100",
                "1234",
                "A200",
                1000,
                1);

        System.out.println(
                "Sender Balance: " +
                        getBalance("A100"));

        System.out.println(
                "Receiver Balance: " +
                        getBalance("A200"));
    }
}