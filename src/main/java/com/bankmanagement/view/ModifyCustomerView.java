package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * View for modifying customer information.
 */
public class ModifyCustomerView extends JInternalFrame {
    private BankController bankController;
    private JTextField customerIdField, fullNameField, fatherNameField, dobField;
    private JTextField addressField, cityField, stateField, mobileField, emailField, nationalityField;
    private JComboBox<String> customerTypeCombo, genderCombo, maritalStatusCombo;
    private JButton loadButton, updateButton;
    
    public ModifyCustomerView() {
        super("Modify Customer", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(600, 500);
        customerIdField = new JTextField(15);
        fullNameField = new JTextField(20);
        fatherNameField = new JTextField(20);
        dobField = new JTextField(20);
        addressField = new JTextField(30);
        cityField = new JTextField(20);
        stateField = new JTextField(20);
        mobileField = new JTextField(20);
        emailField = new JTextField(20);
        nationalityField = new JTextField(20);
        customerTypeCombo = new JComboBox<>(new String[]{"PUBLIC", "STAFF"});
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        maritalStatusCombo = new JComboBox<>(new String[]{"Married", "Unmarried"});
        loadButton = new JButton("Load Customer");
        updateButton = new JButton("Update");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        addLabelAndField(mainPanel, gbc, row++, "Customer ID:", customerIdField);
        mainPanel.add(loadButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = row++;
        addLabelAndField(mainPanel, gbc, row++, "Full Name:", fullNameField);
        addLabelAndField(mainPanel, gbc, row++, "Father Name:", fatherNameField);
        addLabelAndField(mainPanel, gbc, row++, "Date of Birth (YYYY-MM-DD):", dobField);
        addLabelAndCombo(mainPanel, gbc, row++, "Gender:", genderCombo);
        addLabelAndCombo(mainPanel, gbc, row++, "Marital Status:", maritalStatusCombo);
        addLabelAndField(mainPanel, gbc, row++, "Address:", addressField);
        addLabelAndField(mainPanel, gbc, row++, "City:", cityField);
        addLabelAndField(mainPanel, gbc, row++, "State:", stateField);
        addLabelAndField(mainPanel, gbc, row++, "Mobile:", mobileField);
        addLabelAndField(mainPanel, gbc, row++, "Email:", emailField);
        addLabelAndField(mainPanel, gbc, row++, "Nationality:", nationalityField);
        addLabelAndCombo(mainPanel, gbc, row++, "Customer Type:", customerTypeCombo);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(updateButton, gbc);
        
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }
    
    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private void addLabelAndCombo(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComboBox<?> combo) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(combo, gbc);
    }
    
    private void setupListeners() {
        loadButton.addActionListener(e -> loadCustomer());
        updateButton.addActionListener(e -> updateCustomer());
    }
    
    private void loadCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            Customer customer = bankController.getCustomer(customerId);
            
            if (customer != null) {
                fullNameField.setText(customer.getFullName());
                fatherNameField.setText(customer.getFatherName());
                if (customer.getDateOfBirth() != null) {
                    dobField.setText(customer.getDateOfBirth().toString());
                }
                genderCombo.setSelectedItem(customer.getGender());
                maritalStatusCombo.setSelectedItem(customer.getMaritalStatus());
                addressField.setText(customer.getAddress());
                cityField.setText(customer.getCity());
                stateField.setText(customer.getState());
                mobileField.setText(customer.getMobileNumber());
                emailField.setText(customer.getEmail());
                nationalityField.setText(customer.getNationality());
                customerTypeCombo.setSelectedItem(customer.getCustomerType());
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setFullName(fullNameField.getText().trim());
            customer.setFatherName(fatherNameField.getText().trim());
            customer.setDateOfBirth(LocalDate.parse(dobField.getText().trim()));
            customer.setGender((String) genderCombo.getSelectedItem());
            customer.setMaritalStatus((String) maritalStatusCombo.getSelectedItem());
            customer.setAddress(addressField.getText().trim());
            customer.setCity(cityField.getText().trim());
            customer.setState(stateField.getText().trim());
            customer.setMobileNumber(mobileField.getText().trim());
            customer.setEmail(emailField.getText().trim());
            customer.setNationality(nationalityField.getText().trim());
            customer.setCustomerType((String) customerTypeCombo.getSelectedItem());
            
            boolean success = bankController.updateCustomer(customer);
            if (success) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully", 
                                             "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

