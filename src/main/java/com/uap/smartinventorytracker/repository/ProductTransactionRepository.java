package com.uap.smartinventorytracker.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.uap.smartinventorytracker.database.Database;

/**
 * Repository class for handling product transactions in the database.
 * Provides methods to add and list transactions.
 */
public class ProductTransactionRepository {

    /**
     * Adds a new transaction to the database.
     *
     * @param productId       the ID of the product involved in the transaction
     * @param transactionType the type of transaction (e.g., "ADD", "REMOVE")
     * @param quantity        the quantity involved in the transaction
     * @throws SQLException if a database access error occurs
     */
    public void addTransaction(int productId, String transactionType, int quantity) throws SQLException {
        String sql = "INSERT INTO ProductTransaction (product_id, transaction_type, quantity) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setString(2, transactionType);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    /**
     * Lists all transactions from the database, including product details.
     * Joins the Transaction table with the Product table to fetch product names.
     *
     * @throws SQLException if a database access error occurs
     */
    public void listTransactions() throws SQLException {
        String sql = "SELECT t.id, p.name AS product_name, t.transaction_type, t.quantity, t.transaction_date " +
                     "FROM ProductTransaction t JOIN Product p ON t.product_id = p.id";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Product: " + rs.getString("product_name") +
                                   ", Type: " + rs.getString("transaction_type") +
                                   ", Quantity: " + rs.getInt("quantity") +
                                   ", Date: " + rs.getString("transaction_date"));
            }
        }
    }
    
    /**
     * Updates the details of a transaction in the database.
     * This method modifies the transaction type and quantity of an existing transaction.
     * 
     * @param transactionId  the ID of the transaction to be updated
     * @param transactionType the new transaction type (e.g., "ADD", "REMOVE")
     * @param quantity        the new quantity involved in the transaction
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    public void updateTransaction(int transactionId, String transactionType, int quantity) throws SQLException {
        String sql = "UPDATE ProductTransaction SET transaction_type = ?, quantity = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionType);
            stmt.setInt(2, quantity);
            stmt.setInt(3, transactionId);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Deletes a transaction from the database.
     * This method removes a transaction with the specified ID from the database.
     * 
     * @param transactionId  the ID of the transaction to be deleted
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     */
    public void deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM ProductTransaction WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        }
    }


}
