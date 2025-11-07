package com.bankmanagement.controller;

import com.bankmanagement.dao.AccountDAO;
import com.bankmanagement.dao.CustomerDAO;
import com.bankmanagement.dao.TransactionDAO;
import com.bankmanagement.model.Account;
import com.bankmanagement.model.Customer;
import com.bankmanagement.model.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller class for banking operations.
 * Handles business logic for account, customer, and transaction operations.
 */
public class BankController {
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    
    public BankController() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }
    
    /**
     * Creates a new customer and account.
     * 
     * @param customer Customer information
     * @param accountType Account type (SAVINGS or CURRENT)
     * @param modeOfOperation Mode of operation (SELF or JOINT)
     * @param initialDeposit Initial deposit amount
     * @param smsAlert SMS alert enabled
     * @param internetBanking Internet banking enabled
     * @param atmCard ATM card enabled
     * @return Created account
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if invalid parameters
     */
    public Account createCustomerAccount(Customer customer, String accountType, 
                                        String modeOfOperation, BigDecimal initialDeposit,
                                        boolean smsAlert, boolean internetBanking, 
                                        boolean atmCard) throws SQLException {
        // Validate inputs
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
        
        // Create customer
        int customerId = customerDAO.createCustomer(customer);
        customer.setCustomerId(customerId);
        
        // Create account
        Account account = new Account(customerId, accountType, modeOfOperation);
        account.setBalance(initialDeposit);
        account.setSmsAlert(smsAlert);
        account.setInternetBanking(internetBanking);
        account.setAtmCard(atmCard);
        account.setCreatedAt(LocalDateTime.now());
        
        int accountId = accountDAO.createAccount(account);
        account.setAccountId(accountId);
        
        // Create initial deposit transaction if amount > 0
        if (initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(accountId, "DEPOSIT", initialDeposit, initialDeposit);
            transaction.setDescription("Initial deposit");
            transactionDAO.createTransaction(transaction);
        }
        
        return account;
    }
    
    /**
     * Deposits money into an account.
     * 
     * @param accountId Account ID
     * @param amount Amount to deposit
     * @return Updated account
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if invalid parameters
     */
    public Account deposit(int accountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        
        account.deposit(amount);
        accountDAO.updateBalance(accountId, account.getBalance());
        
        // Create transaction record
        Transaction transaction = new Transaction(accountId, "DEPOSIT", amount, account.getBalance());
        transaction.setDescription("Deposit");
        transactionDAO.createTransaction(transaction);
        
        return account;
    }
    
    /**
     * Withdraws money from an account.
     * 
     * @param accountId Account ID
     * @param amount Amount to withdraw
     * @return Updated account
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if invalid parameters or insufficient balance
     */
    public Account withdraw(int accountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        
        account.withdraw(amount);
        accountDAO.updateBalance(accountId, account.getBalance());
        
        // Create transaction record
        Transaction transaction = new Transaction(accountId, "WITHDRAWAL", amount, account.getBalance());
        transaction.setDescription("Withdrawal");
        transactionDAO.createTransaction(transaction);
        
        return account;
    }
    
    /**
     * Transfers money from one account to another.
     * 
     * @param fromAccountId Source account ID
     * @param toAccountId Destination account ID
     * @param amount Amount to transfer
     * @return Updated source account
     * @throws SQLException if database operation fails
     * @throws IllegalArgumentException if invalid parameters or insufficient balance
     */
    public Account transfer(int fromAccountId, int toAccountId, BigDecimal amount) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }
        
        Account fromAccount = accountDAO.getAccountById(fromAccountId);
        Account toAccount = accountDAO.getAccountById(toAccountId);
        
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("One or both accounts not found");
        }
        
        // Withdraw from source account
        fromAccount.withdraw(amount);
        accountDAO.updateBalance(fromAccountId, fromAccount.getBalance());
        
        // Deposit to destination account
        toAccount.deposit(amount);
        accountDAO.updateBalance(toAccountId, toAccount.getBalance());
        
        // Create transaction records
        Transaction fromTransaction = new Transaction(fromAccountId, "TRANSFER", amount, 
                                                     fromAccount.getBalance());
        fromTransaction.setDescription("Transfer to account " + toAccount.getAccountNumber());
        fromTransaction.setRelatedAccountId(toAccountId);
        transactionDAO.createTransaction(fromTransaction);
        
        Transaction toTransaction = new Transaction(toAccountId, "TRANSFER", amount, 
                                                   toAccount.getBalance());
        toTransaction.setDescription("Transfer from account " + fromAccount.getAccountNumber());
        toTransaction.setRelatedAccountId(fromAccountId);
        transactionDAO.createTransaction(toTransaction);
        
        return fromAccount;
    }
    
    /**
     * Gets account balance.
     * 
     * @param accountId Account ID
     * @return Account balance
     * @throws SQLException if database operation fails
     */
    public BigDecimal getBalance(int accountId) throws SQLException {
        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return account.getBalance();
    }
    
    /**
     * Gets transaction history for an account.
     * 
     * @param accountId Account ID
     * @return List of transactions
     * @throws SQLException if database operation fails
     */
    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }
    
    /**
     * Gets customer by ID.
     * 
     * @param customerId Customer ID
     * @return Customer object
     * @throws SQLException if database operation fails
     */
    public Customer getCustomer(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId);
    }
    
    /**
     * Updates customer information.
     * 
     * @param customer Customer with updated information
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    public boolean updateCustomer(Customer customer) throws SQLException {
        return customerDAO.updateCustomer(customer);
    }
    
    /**
     * Deletes a customer and associated accounts.
     * 
     * @param customerId Customer ID
     * @return true if deletion successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteCustomer(int customerId) throws SQLException {
        // Get all accounts for the customer
        List<Account> accounts = accountDAO.getAccountsByCustomerId(customerId);
        
        // Delete all accounts (cascade delete transactions if foreign key constraints are set)
        for (Account account : accounts) {
            accountDAO.deleteAccount(account.getAccountId());
        }
        
        // Delete customer
        return customerDAO.deleteCustomer(customerId);
    }
    
    /**
     * Gets account by ID.
     * 
     * @param accountId Account ID
     * @return Account object
     * @throws SQLException if database operation fails
     */
    public Account getAccount(int accountId) throws SQLException {
        return accountDAO.getAccountById(accountId);
    }
    
    /**
     * Gets all accounts for a customer.
     * 
     * @param customerId Customer ID
     * @return List of accounts
     * @throws SQLException if database operation fails
     */
    public List<Account> getCustomerAccounts(int customerId) throws SQLException {
        return accountDAO.getAccountsByCustomerId(customerId);
    }
}

