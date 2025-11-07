package com.bankmanagement.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window with menu bar and desktop pane.
 */
public class MainView extends JFrame {
    private final JDesktopPane desktopPane;
    
    public MainView() {
        initializeComponents();
        setupMenuBar();
        setupLayout();
    }
    
    private void initializeComponents() {
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Customer Menu
        JMenu customerMenu = new JMenu("Customer");
        JMenuItem newCustomerItem = new JMenuItem("New Account");
        JMenuItem modifyCustomerItem = new JMenuItem("Modify Customer");
        JMenuItem deleteCustomerItem = new JMenuItem("Delete Customer");
        JMenuItem viewCustomersItem = new JMenuItem("View All Customers");
        
        newCustomerItem.addActionListener(e -> openInternalFrame(new NewAccountView()));
        modifyCustomerItem.addActionListener(e -> openInternalFrame(new ModifyCustomerView()));
        deleteCustomerItem.addActionListener(e -> openInternalFrame(new DeleteCustomerView()));
        viewCustomersItem.addActionListener(e -> openInternalFrame(new ViewCustomersView()));
        
        customerMenu.add(newCustomerItem);
        customerMenu.add(modifyCustomerItem);
        customerMenu.add(deleteCustomerItem);
        customerMenu.addSeparator();
        customerMenu.add(viewCustomersItem);
        
        // Transaction Menu
        JMenu transactionMenu = new JMenu("Transaction");
        JMenuItem depositItem = new JMenuItem("Deposit");
        JMenuItem withdrawItem = new JMenuItem("Withdraw");
        JMenuItem transferItem = new JMenuItem("Transfer");
        JMenuItem balanceItem = new JMenuItem("Balance Inquiry");
        JMenuItem statementItem = new JMenuItem("Account Statement");
        
        depositItem.addActionListener(e -> openInternalFrame(new DepositView()));
        withdrawItem.addActionListener(e -> openInternalFrame(new WithdrawView()));
        transferItem.addActionListener(e -> openInternalFrame(new TransferView()));
        balanceItem.addActionListener(e -> openInternalFrame(new BalanceView()));
        statementItem.addActionListener(e -> openInternalFrame(new StatementView()));
        
        transactionMenu.add(depositItem);
        transactionMenu.add(withdrawItem);
        transactionMenu.add(transferItem);
        transactionMenu.addSeparator();
        transactionMenu.add(balanceItem);
        transactionMenu.add(statementItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        aboutItem.addActionListener(e -> showAboutDialog());
        exitItem.addActionListener(e -> System.exit(0));
        
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        helpMenu.add(exitItem);
        
        menuBar.add(customerMenu);
        menuBar.add(transactionMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(desktopPane, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.add(new JLabel("Bank Management System v1.0"));
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private void openInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        
        // Center the frame
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((desktopSize.width - frameSize.width) / 2,
                         (desktopSize.height - frameSize.height) / 2);
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Bank Management System\n\n" +
            "Version 1.0.0\n" +
            "Professional Java Banking Application\n\n" +
            "Built with Swing and MySQL",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }
}

