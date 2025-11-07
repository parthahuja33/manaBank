package com.bankmanagement.dao;

import com.bankmanagement.model.Account;
import com.bankmanagement.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Account operations.
 */
public class AccountDAO {
    private Connection connection;
    
    public AccountDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Creates a new account in the database.
     * 
     * @param account Account object to create
     * @return Generated account ID
     * @throws SQLException if database operation fails
     */
    public int createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (customer_id, account_number, account_type, " +
                     "mode_of_operation, balance, sms_alert, internet_banking, atm_card, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String accountNumber = generateAccountNumber();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, account.getCustomerId());
            pstmt.setString(2, accountNumber);
            pstmt.setString(3, account.getAccountType());
            pstmt.setString(4, account.getModeOfOperation());
            pstmt.setBigDecimal(5, account.getBalance());
            pstmt.setBoolean(6, account.isSmsAlert());
            pstmt.setBoolean(7, account.isInternetBanking());
            pstmt.setBoolean(8, account.isAtmCard());
            pstmt.setTimestamp(9, Timestamp.valueOf(account.getCreatedAt()));
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountId = generatedKeys.getInt(1);
                    account.setAccountNumber(accountNumber);
                    connection.commit();
                    return accountId;
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Generates a unique account number.
     */
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }
    
    /**
     * Retrieves an account by ID.
     * 
     * @param accountId Account ID
     * @return Account object or null if not found
     * @throws SQLException if database operation fails
     */
    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves an account by account number.
     * 
     * @param accountNumber Account number
     * @return Account object or null if not found
     * @throws SQLException if database operation fails
     */
    public Account getAccountByNumber(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all accounts for a customer.
     * 
     * @param customerId Customer ID
     * @return List of accounts
     * @throws SQLException if database operation fails
     */
    public List<Account> getAccountsByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY account_id";
        List<Account> accounts = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs));
                }
            }
        }
        return accounts;
    }
    
    /**
     * Updates account balance.
     * 
     * @param accountId Account ID
     * @param newBalance New balance
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    public boolean updateBalance(int accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, newBalance);
            pstmt.setInt(2, accountId);
            
            int affectedRows = pstmt.executeUpdate();
            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Deletes an account by ID.
     * 
     * @param accountId Account ID to delete
     * @return true if deletion successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteAccount(int accountId) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            
            int affectedRows = pstmt.executeUpdate();
            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Maps a ResultSet row to an Account object.
     */
    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setCustomerId(rs.getInt("customer_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAccountType(rs.getString("account_type"));
        account.setModeOfOperation(rs.getString("mode_of_operation"));
        account.setBalance(rs.getBigDecimal("balance"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            account.setCreatedAt(createdAt.toLocalDateTime());
        }
        account.setSmsAlert(rs.getBoolean("sms_alert"));
        account.setInternetBanking(rs.getBoolean("internet_banking"));
        account.setAtmCard(rs.getBoolean("atm_card"));
        return account;
    }
}

