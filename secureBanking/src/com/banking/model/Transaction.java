package com.banking.model;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL }

    private final String transactionId;
    private final String accountId;
    private final double amount;
    private final long timestamp;
    private final Type type;

    public Transaction(String transactionId, String accountId, double amount, long timestamp, Type type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public Type getType() { return type; }

    @Override
    public String toString() {
        return transactionId + "," + accountId + "," + amount + "," + timestamp + "," + type;
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) throw new IllegalArgumentException("Invalid transaction line");
        return new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]), Long.parseLong(parts[3]), Type.valueOf(parts[4]));
    }
}
