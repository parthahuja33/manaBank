package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * View for displaying account statement.
 */
public class StatementView extends JInternalFrame {
    private BankController bankController;
    private JTextField accountIdField;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JButton viewButton;
    
    public StatementView() {
        super("Account Statement", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(800, 500);
        accountIdField = new JTextField(15);
        viewButton = new JButton("View Statement");
        
        String[] columnNames = {"Transaction ID", "Type", "Amount", "Balance After", "Description", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Account ID:"));
        topPanel.add(accountIdField);
        topPanel.add(viewButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        viewButton.addActionListener(e -> loadStatement());
    }
    
    private void loadStatement() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText().trim());
            List<Transaction> transactions = bankController.getTransactionHistory(accountId);
            
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Add transactions to table
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Transaction transaction : transactions) {
                Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    transaction.getBalanceAfter(),
                    transaction.getDescription(),
                    dateFormat.format(java.sql.Timestamp.valueOf(transaction.getTransactionDate()))
                };
                tableModel.addRow(row);
            }
            
            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No transactions found for this account", 
                                             "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

