package com.banking.service;

import com.banking.model.User;
import com.banking.data.DataStore;
import com.banking.util.SecurityUtils;
import java.util.*;

public class AuthService {
    private List<User> users;

    public AuthService() {
        users = DataStore.loadUsers();
    }

    public User register(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return null; // Username already exists
            }
        }
        String saltAndHash = SecurityUtils.hashPassword(password);
        String[] parts = saltAndHash.split(":");
        String salt = parts[0];
        String hash = parts[1];
        String accountId = UUID.randomUUID().toString();
        User user = new User(username, hash, salt, accountId);
        users.add(user);
        DataStore.saveUsers(users);
        return user;
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                String storedSaltAndHash = u.getSalt() + ":" + u.getPasswordHash();
                if (SecurityUtils.verifyPassword(password, storedSaltAndHash)) {
                    return u;
                }
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
}
