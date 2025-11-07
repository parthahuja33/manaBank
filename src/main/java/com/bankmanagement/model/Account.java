package com.bankmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model class representing a Bank Account.
 */
public class Account {
    private int accountId;
    private int customerId;
    private String accountNumber;
    private String accountType; // SAVINGS or CURRENT
    private String modeOfOperation; // SELF or JOINT
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private boolean smsAlert;
    private boolean internetBanking;
    private boolean atmCard;
    
    // Constructors
    public Account() {
        this.balance = BigDecimal.ZERO;
    }
    
    public Account(int customerId, String accountType, String modeOfOperation) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.modeOfOperation = modeOfOperation;
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public String getModeOfOperation() {
        return modeOfOperation;
    }
    
    public void setModeOfOperation(String modeOfOperation) {
        this.modeOfOperation = modeOfOperation;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isSmsAlert() {
        return smsAlert;
    }
    
    public void setSmsAlert(boolean smsAlert) {
        this.smsAlert = smsAlert;
    }
    
    public boolean isInternetBanking() {
        return internetBanking;
    }
    
    public void setInternetBanking(boolean internetBanking) {
        this.internetBanking = internetBanking;
    }
    
    public boolean isAtmCard() {
        return atmCard;
    }
    
    public void setAtmCard(boolean atmCard) {
        this.atmCard = atmCard;
    }
    
    /**
     * Deposits money into the account.
     * 
     * @param amount Amount to deposit
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }
    
    /**
     * Withdraws money from the account.
     * 
     * @param amount Amount to withdraw
     * @throws IllegalArgumentException if amount is negative or exceeds balance
     */
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}

