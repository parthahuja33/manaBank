package com.bankmanagement.controller;

import com.bankmanagement.dao.UserDAO;
import com.bankmanagement.model.User;

import java.sql.SQLException;

/**
 * Controller for authentication operations.
 */
public class AuthController {
    private UserDAO userDAO;
    
    public AuthController() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Authenticates a user.
     * 
     * @param username Username
     * @param password Password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String username, String password) {
        try {
            return userDAO.authenticate(username, password);
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return null;
        }
    }
}

