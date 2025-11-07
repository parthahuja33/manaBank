package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Account;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * View for withdrawing money from an account.
 */
public class WithdrawView extends JInternalFrame {
    private final BankController bankController;
    private final JTextField accountIdField, amountField, balanceField;
    
    public WithdrawView() {
        super("Withdraw", true, true, true, true);
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
        addLabelAndField(this, gbc, row++, "Amount to Withdraw:", amountField);
        addLabelAndField(this, gbc, row++, "Current Balance:", balanceField);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton checkButton = new JButton("Check Balance");
        checkButton.addActionListener(e -> checkBalance());
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> performWithdraw());
        buttonPanel.add(checkButton);
        buttonPanel.add(withdrawButton);
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
    
    private void performWithdraw() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText().trim());
            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            
            Account account = bankController.withdraw(accountId, amount);
            JOptionPane.showMessageDialog(this, 
                "Withdrawal successful!\n\n" +
                "Amount Withdrawn: " + amount + "\n" +
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

