package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;

import javax.swing.*;
import java.awt.*;

/**
 * View for deleting a customer.
 */
public class DeleteCustomerView extends JInternalFrame {
    private BankController bankController;
    private JTextField customerIdField;
    private JButton deleteButton, checkButton;
    private JTextArea customerInfoArea;
    
    public DeleteCustomerView() {
        super("Delete Customer", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 400);
        customerIdField = new JTextField(15);
        deleteButton = new JButton("Delete Customer");
        checkButton = new JButton("Check Customer");
        customerInfoArea = new JTextArea(10, 30);
        customerInfoArea.setEditable(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Customer ID:"));
        topPanel.add(customerIdField);
        topPanel.add(checkButton);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(deleteButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(customerInfoArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        checkButton.addActionListener(e -> checkCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
    }
    
    private void checkCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            var customer = bankController.getCustomer(customerId);
            
            if (customer != null) {
                customerInfoArea.setText(
                    "Customer ID: " + customer.getCustomerId() + "\n" +
                    "Full Name: " + customer.getFullName() + "\n" +
                    "Email: " + customer.getEmail() + "\n" +
                    "Mobile: " + customer.getMobileNumber() + "\n" +
                    "City: " + customer.getCity() + "\n" +
                    "State: " + customer.getState()
                );
            } else {
                customerInfoArea.setText("Customer not found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this customer and all associated accounts?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = bankController.deleteCustomer(customerId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully", 
                                                 "Success", JOptionPane.INFORMATION_MESSAGE);
                    customerIdField.setText("");
                    customerInfoArea.setText("");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

