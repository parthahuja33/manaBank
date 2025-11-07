package com.bankmanagement.view;

import com.bankmanagement.dao.CustomerDAO;
import com.bankmanagement.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View for displaying all customers.
 */
public class ViewCustomersView extends JInternalFrame {
    private final CustomerDAO customerDAO;
    private final DefaultTableModel tableModel;
    
    public ViewCustomersView() {
        super("View All Customers", true, true, true, true);
        this.customerDAO = new CustomerDAO();
        initializeComponents();
        setupLayout();
        setupListeners();
        loadCustomers();
    }
    
    private void initializeComponents() {
        setSize(900, 500);
        
        String[] columnNames = {"Customer ID", "Full Name", "Email", "Mobile", "City", "State", "Customer Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadCustomers());
        topPanel.add(refreshButton);
        
        JTable customerTable = new JTable(tableModel);
        customerTable.setFillsViewportHeight(true);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        // Listeners set up in setupLayout
    }
    
    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Add customers to table
            for (Customer customer : customers) {
                Object[] row = {
                    customer.getCustomerId(),
                    customer.getFullName(),
                    customer.getEmail(),
                    customer.getMobileNumber(),
                    customer.getCity(),
                    customer.getState(),
                    customer.getCustomerType()
                };
                tableModel.addRow(row);
            }
            
            // Table will be empty if no customers - no need to show message
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

