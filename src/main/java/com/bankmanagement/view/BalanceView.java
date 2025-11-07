package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Account;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * View for checking account balance.
 */
public class BalanceView extends JInternalFrame {
    private final BankController bankController;
    private final JTextField accountIdField, balanceField, accountNumberField, accountTypeField;
    
    public BalanceView() {
        super("Balance Inquiry", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 300);
        accountIdField = new JTextField(15);
        balanceField = new JTextField(15);
        balanceField.setEditable(false);
        accountNumberField = new JTextField(15);
        accountNumberField.setEditable(false);
        accountTypeField = new JTextField(15);
        accountTypeField.setEditable(false);
    }
    
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        addLabelAndField(this, gbc, row++, "Account ID:", accountIdField);
        addLabelAndField(this, gbc, row++, "Account Number:", accountNumberField);
        addLabelAndField(this, gbc, row++, "Account Type:", accountTypeField);
        addLabelAndField(this, gbc, row++, "Balance:", balanceField);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkButton = new JButton("Check Balance");
        checkButton.addActionListener(e -> checkBalance());
        add(checkButton, gbc);
    }
    
    private void addLabelAndField(Container container, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        container.add(field, gbc);
    }
    
    private void setupListeners() {
        // Listeners set up in setupLayout
    }
    
    private void checkBalance() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText().trim());
            Account account = bankController.getAccount(accountId);
            
            if (account != null) {
                balanceField.setText(account.getBalance().toString());
                accountNumberField.setText(account.getAccountNumber());
                accountTypeField.setText(account.getAccountType());
            } else {
                JOptionPane.showMessageDialog(this, "Account not found", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

