package com.banking.model;

public class User {
    private final String username;
    private String passwordHash;
    private String salt;
    private String accountId;

    public User(String username, String passwordHash, String salt, String accountId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.accountId = accountId;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getSalt() { return salt; }
    public String getAccountId() { return accountId; }

    @Override
    public String toString() {
        return username + "," + passwordHash + "," + salt + "," + accountId;
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) throw new IllegalArgumentException("Invalid user line");
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
