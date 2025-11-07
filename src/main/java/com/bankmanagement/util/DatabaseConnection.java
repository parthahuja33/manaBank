package com.bankmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing database connections.
 * Provides a single point of access to the database connection.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes the database connection.
     */
    private DatabaseConnection() {
        // Driver is auto-loaded in JDBC 4.0+, no need for Class.forName
    }
    
    /**
     * Returns the singleton instance of DatabaseConnection.
     * 
     * @return DatabaseConnection instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Returns the database connection.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
        }
        return connection;
    }
    
    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}

