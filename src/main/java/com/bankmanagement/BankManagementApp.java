package com.bankmanagement;

import com.bankmanagement.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main application entry point for Bank Management System.
 */
public class BankManagementApp {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}

