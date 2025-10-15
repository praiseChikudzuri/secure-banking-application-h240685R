package com.banking.service;

import com.banking.model.*;
import com.banking.data.DataStore;
import java.util.*;

public class AccountService {
    private List<Account> accounts;
    private List<Transaction> transactions;

    public AccountService() {
        accounts = DataStore.loadAccounts();
        transactions = DataStore.loadTransactions();
    }

    public Account getAccount(String accountId) {
        for (Account a : accounts) {
            if (a.getAccountId().equals(accountId)) {
                return a;
            }
        }
        return null;
    }

    public boolean deposit(String accountId, double amount) {
        Account account = getAccount(accountId);
        if (account == null || amount <= 0) return false;
        if (!account.deposit(amount)) return false;
        Transaction t = new Transaction(UUID.randomUUID().toString(), accountId, amount, System.currentTimeMillis(), Transaction.Type.DEPOSIT);
        transactions.add(t);
        DataStore.saveAccounts(accounts);
        DataStore.saveTransactions(transactions);
        return true;
    }

    public boolean withdraw(String accountId, double amount) {
        Account account = getAccount(accountId);
        if (account == null || amount <= 0) return false;
        if (!account.withdraw(amount)) return false;
        Transaction t = new Transaction(UUID.randomUUID().toString(), accountId, amount, System.currentTimeMillis(), Transaction.Type.WITHDRAWAL);
        transactions.add(t);
        DataStore.saveAccounts(accounts);
        DataStore.saveTransactions(transactions);
        return true;
    }

    public List<Transaction> getTransactionsForAccount(String accountId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountId().equals(accountId)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
