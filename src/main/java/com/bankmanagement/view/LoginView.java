package com.bankmanagement.view;

import com.bankmanagement.controller.AuthController;
import com.bankmanagement.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Login view for user authentication.
 */
public class LoginView extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final AuthController authController;
    
    public LoginView() {
        this.authController = new AuthController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setTitle("Bank Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Bank Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.addActionListener(e -> performLogin());
        mainPanel.add(loginButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        // Allow login on Enter key in password field
        passwordField.addActionListener(e -> performLogin());
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", 
                                         "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            User user = authController.authenticate(username, password);
            if (user != null) {
                // Login successful, open main application window
                this.dispose();
                new MainView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", 
                                             "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during login: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

