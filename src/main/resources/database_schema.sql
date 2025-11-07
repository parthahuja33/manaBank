-- Bank Management System Database Schema
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS bankmanagement;
USE bankmanagement;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'TELLER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100),
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    marital_status VARCHAR(20),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    mobile_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    nationality VARCHAR(50),
    customer_type VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts table
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type VARCHAR(20) NOT NULL, -- SAVINGS or CURRENT
    mode_of_operation VARCHAR(20) NOT NULL, -- SELF or JOINT
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    sms_alert BOOLEAN DEFAULT FALSE,
    internet_banking BOOLEAN DEFAULT FALSE,
    atm_card BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id),
    INDEX idx_account_number (account_number)
);

-- Transactions table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL, -- DEPOSIT, WITHDRAWAL, TRANSFER
    amount DECIMAL(15, 2) NOT NULL,
    balance_after DECIMAL(15, 2) NOT NULL,
    description TEXT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    related_account_id INT, -- For transfer transactions
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (related_account_id) REFERENCES accounts(account_id) ON DELETE SET NULL,
    INDEX idx_account_id (account_id),
    INDEX idx_transaction_date (transaction_date)
);

-- Insert default admin user (username: admin, password: admin123)
INSERT INTO users (username, password, role) 
VALUES ('admin', 'admin123', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

-- Insert default teller user (username: teller, password: teller123)
INSERT INTO users (username, password, role) 
VALUES ('teller', 'teller123', 'TELLER')
ON DUPLICATE KEY UPDATE username=username;

