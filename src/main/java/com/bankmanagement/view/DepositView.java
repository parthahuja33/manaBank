package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Account;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * View for depositing money into an account.
 */
public class DepositView extends JInternalFrame {
    private final BankController bankController;
    private final JTextField accountIdField, amountField, balanceField;
    
    public DepositView() {
        super("Deposit", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 300);
        accountIdField = new JTextField(15);
        amountField = new JTextField(15);
        balanceField = new JTextField(15);
        balanceField.setEditable(false);
    }
    
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        addLabelAndField(this, gbc, row++, "Account ID:", accountIdField);
        addLabelAndField(this, gbc, row++, "Amount to Deposit:", amountField);
        addLabelAndField(this, gbc, row++, "Current Balance:", balanceField);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton checkButton = new JButton("Check Balance");
        checkButton.addActionListener(e -> checkBalance());
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> performDeposit());
        buttonPanel.add(checkButton);
        buttonPanel.add(depositButton);
        add(buttonPanel, gbc);
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
            BigDecimal balance = bankController.getBalance(accountId);
            balanceField.setText(balance.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performDeposit() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText().trim());
            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            
            Account account = bankController.deposit(accountId, amount);
            JOptionPane.showMessageDialog(this, 
                "Deposit successful!\n\n" +
                "Amount Deposited: " + amount + "\n" +
                "New Balance: " + account.getBalance(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            balanceField.setText(account.getBalance().toString());
            amountField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

