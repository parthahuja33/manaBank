package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Account;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * View for transferring money between accounts.
 */
public class TransferView extends JInternalFrame {
    private final BankController bankController;
    private final JTextField fromAccountField, toAccountField, amountField;
    
    public TransferView() {
        super("Transfer", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 250);
        fromAccountField = new JTextField(15);
        toAccountField = new JTextField(15);
        amountField = new JTextField(15);
    }
    
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        addLabelAndField(this, gbc, row++, "From Account ID:", fromAccountField);
        addLabelAndField(this, gbc, row++, "To Account ID:", toAccountField);
        addLabelAndField(this, gbc, row++, "Amount:", amountField);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> performTransfer());
        add(transferButton, gbc);
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
    
    private void performTransfer() {
        try {
            int fromAccountId = Integer.parseInt(fromAccountField.getText().trim());
            int toAccountId = Integer.parseInt(toAccountField.getText().trim());
            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            
            Account account = bankController.transfer(fromAccountId, toAccountId, amount);
            JOptionPane.showMessageDialog(this, 
                "Transfer successful!\n\n" +
                "Amount Transferred: " + amount + "\n" +
                "From Account Balance: " + account.getBalance(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            fromAccountField.setText("");
            toAccountField.setText("");
            amountField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

