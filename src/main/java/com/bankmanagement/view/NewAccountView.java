package com.bankmanagement.view;

import com.bankmanagement.controller.BankController;
import com.bankmanagement.model.Account;
import com.bankmanagement.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * View for creating a new customer account.
 */
public class NewAccountView extends JInternalFrame {
    private BankController bankController;
    
    // Form fields
    private JTextField fullNameField, fatherNameField, dobField, addressField;
    private JTextField cityField, stateField, mobileField, emailField, nationalityField;
    private JTextField initialDepositField;
    private JComboBox<String> accountTypeCombo, customerTypeCombo, genderCombo;
    private JComboBox<String> maritalStatusCombo, modeOfOperationCombo;
    private JCheckBox smsAlertCheck, internetBankingCheck, atmCardCheck;
    private JTextArea addressArea;
    private JButton createButton, clearButton;
    
    public NewAccountView() {
        super("New Account", true, true, true, true);
        this.bankController = new BankController();
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        setSize(900, 700);
        
        // Initialize fields
        fullNameField = new JTextField(20);
        fatherNameField = new JTextField(20);
        dobField = new JTextField(20);
        dobField.setToolTipText("Format: YYYY-MM-DD (e.g., 1990-01-15)");
        addressArea = new JTextArea(3, 30);
        cityField = new JTextField(20);
        stateField = new JTextField(20);
        mobileField = new JTextField(20);
        emailField = new JTextField(20);
        nationalityField = new JTextField(20);
        initialDepositField = new JTextField(20);
        
        accountTypeCombo = new JComboBox<>(new String[]{"SAVINGS", "CURRENT"});
        customerTypeCombo = new JComboBox<>(new String[]{"PUBLIC", "STAFF"});
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        maritalStatusCombo = new JComboBox<>(new String[]{"Married", "Unmarried"});
        modeOfOperationCombo = new JComboBox<>(new String[]{"SELF", "JOINT"});
        
        smsAlertCheck = new JCheckBox("SMS Alert");
        internetBankingCheck = new JCheckBox("Internet Banking");
        atmCardCheck = new JCheckBox("ATM Card");
        
        createButton = new JButton("Create Account");
        clearButton = new JButton("Clear");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // Personal Details Section
        addSectionHeader(mainPanel, gbc, row++, "Personal Details");
        
        addLabelAndField(mainPanel, gbc, row++, "Full Name *", fullNameField);
        addLabelAndField(mainPanel, gbc, row++, "Father/Husband Name", fatherNameField);
        addLabelAndField(mainPanel, gbc, row++, "Date of Birth * (YYYY-MM-DD)", dobField);
        addLabelAndCombo(mainPanel, gbc, row++, "Gender *", genderCombo);
        addLabelAndCombo(mainPanel, gbc, row++, "Marital Status", maritalStatusCombo);
        
        // Address Section
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Address:"), gbc);
        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(new JScrollPane(addressArea), gbc);
        gbc.fill = GridBagConstraints.NONE;
        
        addLabelAndField(mainPanel, gbc, row++, "City", cityField);
        addLabelAndField(mainPanel, gbc, row++, "State", stateField);
        addLabelAndField(mainPanel, gbc, row++, "Mobile Number *", mobileField);
        addLabelAndField(mainPanel, gbc, row++, "Email", emailField);
        addLabelAndField(mainPanel, gbc, row++, "Nationality", nationalityField);
        
        // Account Details Section
        addSectionHeader(mainPanel, gbc, row++, "Account Details");
        addLabelAndCombo(mainPanel, gbc, row++, "Account Type *", accountTypeCombo);
        addLabelAndCombo(mainPanel, gbc, row++, "Customer Type *", customerTypeCombo);
        addLabelAndCombo(mainPanel, gbc, row++, "Mode of Operation *", modeOfOperationCombo);
        addLabelAndField(mainPanel, gbc, row++, "Initial Deposit", initialDepositField);
        
        // Services Section
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Services:"), gbc);
        gbc.gridy = row++;
        JPanel servicesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        servicesPanel.add(smsAlertCheck);
        servicesPanel.add(internetBankingCheck);
        servicesPanel.add(atmCardCheck);
        mainPanel.add(servicesPanel, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton);
        buttonPanel.add(clearButton);
        mainPanel.add(buttonPanel, gbc);
        
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }
    
    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, int row, String text) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, gbc);
        gbc.gridwidth = 1;
    }
    
    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private void addLabelAndCombo(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComboBox<?> combo) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(combo, gbc);
    }
    
    private void setupListeners() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }
    
    private void createAccount() {
        try {
            // Validate required fields
            if (fullNameField.getText().trim().isEmpty() || 
                dobField.getText().trim().isEmpty() ||
                mobileField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (*)", 
                                             "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Parse date
            LocalDate dateOfBirth = LocalDate.parse(dobField.getText().trim());
            
            // Create customer
            Customer customer = new Customer();
            customer.setFullName(fullNameField.getText().trim());
            customer.setFatherName(fatherNameField.getText().trim());
            customer.setDateOfBirth(dateOfBirth);
            customer.setGender((String) genderCombo.getSelectedItem());
            customer.setMaritalStatus((String) maritalStatusCombo.getSelectedItem());
            customer.setAddress(addressArea.getText().trim());
            customer.setCity(cityField.getText().trim());
            customer.setState(stateField.getText().trim());
            customer.setMobileNumber(mobileField.getText().trim());
            customer.setEmail(emailField.getText().trim());
            customer.setNationality(nationalityField.getText().trim());
            customer.setCustomerType((String) customerTypeCombo.getSelectedItem());
            
            // Parse initial deposit
            BigDecimal initialDeposit = BigDecimal.ZERO;
            if (!initialDepositField.getText().trim().isEmpty()) {
                initialDeposit = new BigDecimal(initialDepositField.getText().trim());
            }
            
            // Create account
            Account account = bankController.createCustomerAccount(
                customer,
                (String) accountTypeCombo.getSelectedItem(),
                (String) modeOfOperationCombo.getSelectedItem(),
                initialDeposit,
                smsAlertCheck.isSelected(),
                internetBankingCheck.isSelected(),
                atmCardCheck.isSelected()
            );
            
            JOptionPane.showMessageDialog(this, 
                "Account created successfully!\n\n" +
                "Customer ID: " + customer.getCustomerId() + "\n" +
                "Account ID: " + account.getAccountId() + "\n" +
                "Account Number: " + account.getAccountNumber() + "\n" +
                "Balance: " + account.getBalance(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void clearForm() {
        fullNameField.setText("");
        fatherNameField.setText("");
        dobField.setText("");
        addressArea.setText("");
        cityField.setText("");
        stateField.setText("");
        mobileField.setText("");
        emailField.setText("");
        nationalityField.setText("");
        initialDepositField.setText("");
        accountTypeCombo.setSelectedIndex(0);
        customerTypeCombo.setSelectedIndex(0);
        genderCombo.setSelectedIndex(0);
        maritalStatusCombo.setSelectedIndex(0);
        modeOfOperationCombo.setSelectedIndex(0);
        smsAlertCheck.setSelected(false);
        internetBankingCheck.setSelected(false);
        atmCardCheck.setSelected(false);
    }
}

