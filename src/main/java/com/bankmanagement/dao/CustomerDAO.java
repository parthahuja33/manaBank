package com.bankmanagement.dao;

import com.bankmanagement.model.Customer;
import com.bankmanagement.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Customer operations.
 */
public class CustomerDAO {
    private Connection connection;
    
    public CustomerDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Creates a new customer in the database.
     * 
     * @param customer Customer object to create
     * @return Generated customer ID
     * @throws SQLException if database operation fails
     */
    public int createCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (full_name, father_name, date_of_birth, gender, " +
                     "marital_status, address, city, state, mobile_number, email, nationality, customer_type) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getFatherName());
            pstmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            pstmt.setString(4, customer.getGender());
            pstmt.setString(5, customer.getMaritalStatus());
            pstmt.setString(6, customer.getAddress());
            pstmt.setString(7, customer.getCity());
            pstmt.setString(8, customer.getState());
            pstmt.setString(9, customer.getMobileNumber());
            pstmt.setString(10, customer.getEmail());
            pstmt.setString(11, customer.getNationality());
            pstmt.setString(12, customer.getCustomerType());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    connection.commit();
                    return customerId;
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Retrieves a customer by ID.
     * 
     * @param customerId Customer ID
     * @return Customer object or null if not found
     * @throws SQLException if database operation fails
     */
    public Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Updates customer information.
     * 
     * @param customer Customer object with updated information
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET full_name = ?, father_name = ?, date_of_birth = ?, " +
                     "gender = ?, marital_status = ?, address = ?, city = ?, state = ?, " +
                     "mobile_number = ?, email = ?, nationality = ?, customer_type = ? " +
                     "WHERE customer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getFatherName());
            pstmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            pstmt.setString(4, customer.getGender());
            pstmt.setString(5, customer.getMaritalStatus());
            pstmt.setString(6, customer.getAddress());
            pstmt.setString(7, customer.getCity());
            pstmt.setString(8, customer.getState());
            pstmt.setString(9, customer.getMobileNumber());
            pstmt.setString(10, customer.getEmail());
            pstmt.setString(11, customer.getNationality());
            pstmt.setString(12, customer.getCustomerType());
            pstmt.setInt(13, customer.getCustomerId());
            
            int affectedRows = pstmt.executeUpdate();
            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Deletes a customer by ID.
     * 
     * @param customerId Customer ID to delete
     * @return true if deletion successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            
            int affectedRows = pstmt.executeUpdate();
            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    
    /**
     * Retrieves all customers.
     * 
     * @return List of all customers
     * @throws SQLException if database operation fails
     */
    public List<Customer> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        List<Customer> customers = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }
    
    /**
     * Maps a ResultSet row to a Customer object.
     */
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFullName(rs.getString("full_name"));
        customer.setFatherName(rs.getString("father_name"));
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            customer.setDateOfBirth(dob.toLocalDate());
        }
        customer.setGender(rs.getString("gender"));
        customer.setMaritalStatus(rs.getString("marital_status"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setMobileNumber(rs.getString("mobile_number"));
        customer.setEmail(rs.getString("email"));
        customer.setNationality(rs.getString("nationality"));
        customer.setCustomerType(rs.getString("customer_type"));
        return customer;
    }
}

