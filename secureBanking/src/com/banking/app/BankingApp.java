package com.banking.app;

import com.banking.service.*;
import com.banking.model.*;
import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        AccountService accountService = new AccountService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            System.out.println("\nWelcome to SecureBanking!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                currentUser = authService.login(username, password);
                if (currentUser != null) {
                    System.out.println("Login successful!");
                    break;
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Choose a username: ");
                String username = scanner.nextLine();
                System.out.print("Choose a password: ");
                String password = scanner.nextLine();
                User user = authService.register(username, password);
                if (user != null) {
                    System.out.println("Registration successful! Please login.");
                } else {
                    System.out.println("Username already exists.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }

        // Authenticated user loop
        while (true) {
            Account account = accountService.getAccount(currentUser.getAccountId());
            System.out.println("\nWelcome, " + currentUser.getUsername() + "!");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transactions");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                if (account != null) {
                    System.out.printf("Current balance: $%.2f\n", account.getBalance());
                } else {
                    System.out.println("Account not found.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter deposit amount: ");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (accountService.deposit(currentUser.getAccountId(), amount)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed. Amount must be positive.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount.");
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter withdrawal amount: ");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (accountService.withdraw(currentUser.getAccountId(), amount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed. Check your balance and amount.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount.");
                }
            } else if (choice.equals("4")) {
                System.out.println("Recent Transactions:");
                for (Transaction t : accountService.getTransactionsForAccount(currentUser.getAccountId())) {
                    System.out.println(t);
                }
            } else if (choice.equals("5")) {
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
