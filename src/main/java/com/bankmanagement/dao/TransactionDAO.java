package com.bankmanagement.dao;

import com.bankmanagement.model.Transaction;
import com.bankmanagement.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction operations.
 */
public class TransactionDAO {
    private Connection connection;
    
    public TransactionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Creates a new transaction in the database.
     * 
     * @param transaction Transaction object to create
     * @return Generated transaction ID
     * @throws SQLException if database operation fails
     */
    public int createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount, " +
                     "balance_after, description, transaction_date, related_account_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setString(2, transaction.getTransactionType());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setBigDecimal(4, transaction.getBalanceAfter());
            pstmt.setString(5, transaction.getDescription());
            pstmt.setTimestamp(6, Timestamp.valueOf(transaction.getTransactionDate()));
            if (transaction.getRelatedAccountId() != null) {
                pstmt.setInt(7, transaction.getRelatedAccountId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);
                    connection.commit();
                    return transactionId;
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Retrieves all transactions for an account.
     * 
     * @param accountId Account ID
     * @return List of transactions
     * @throws SQLException if database operation fails
     */
    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }
        return transactions;
    }
    
    /**
     * Retrieves transactions for an account within a date range.
     * 
     * @param accountId Account ID
     * @param startDate Start date
     * @param endDate End date
     * @return List of transactions
     * @throws SQLException if database operation fails
     */
    public List<Transaction> getTransactionsByDateRange(int accountId, LocalDateTime startDate, 
                                                         LocalDateTime endDate) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE account_id = ? " +
                     "AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setTimestamp(2, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(3, Timestamp.valueOf(endDate));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        }
        return transactions;
    }
    
    /**
     * Retrieves a transaction by ID.
     * 
     * @param transactionId Transaction ID
     * @return Transaction object or null if not found
     * @throws SQLException if database operation fails
     */
    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Maps a ResultSet row to a Transaction object.
     */
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setAccountId(rs.getInt("account_id"));
        transaction.setTransactionType(rs.getString("transaction_type"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
        transaction.setDescription(rs.getString("description"));
        Timestamp transactionDate = rs.getTimestamp("transaction_date");
        if (transactionDate != null) {
            transaction.setTransactionDate(transactionDate.toLocalDateTime());
        }
        int relatedAccountId = rs.getInt("related_account_id");
        if (!rs.wasNull()) {
            transaction.setRelatedAccountId(relatedAccountId);
        }
        return transaction;
    }
}

