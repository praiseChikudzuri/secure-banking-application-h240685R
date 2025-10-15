package com.banking.model;

public class Account {
    private final String accountId;
    private final String accountNumber;
    private double balance;

    public Account(String accountId, String accountNumber, double balance) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountId() { return accountId; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return accountId + "," + accountNumber + "," + balance;
    }

    public static Account fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid account line");
        return new Account(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
}
