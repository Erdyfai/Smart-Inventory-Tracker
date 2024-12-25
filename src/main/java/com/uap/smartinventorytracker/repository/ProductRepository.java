package com.uap.smartinventorytracker.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.uap.smartinventorytracker.database.Database;

/**
 * Repository class for managing products in the inventory.
 * Provides methods to add new products, list all existing products, update, and delete products.
 */
public class ProductRepository {
	
	

    /**
     * Adds a new product to the database.
     *
     * @param name       the name of the product
     * @param quantity   the quantity of the product
     * @param expiryDate the expiry date of the product in YYYY-MM-DD format
     * @param typeId     the ID of the product type (foreign key to the ProductType table)
     * @throws SQLException if a database access error occurs
     */
    public void addProduct(String name, int quantity, String expiryDate, int typeId) throws SQLException {
        String sql = "INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, expiryDate);
            stmt.setInt(4, typeId);
            stmt.executeUpdate();
        }
    }

    /**
     * Lists all products from the database, including their type information.
     * Joins the Product table with the ProductType table to fetch type names.
     *
     * @return A string representation of all products, including type information.
     * @throws SQLException if a database access error occurs.
     */
    public String listProducts() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT p.id, p.name, p.quantity, p.expiry_date, t.name AS type_name " +
                     "FROM Product p JOIN ProductType t ON p.type_id = t.id";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Name: ").append(rs.getString("name"))
                      .append(", Quantity: ").append(rs.getInt("quantity"))
                      .append(", Expiry Date: ").append(rs.getString("expiry_date"))
                      .append(", Type: ").append(rs.getString("type_name"))
                      .append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }

    /**
     * Deletes a product from the database by its ID.
     *
     * @param productId the ID of the product to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM Product WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing product's details.
     *
     * @param productId  the ID of the product to update
     * @param name       the new name of the product
     * @param quantity   the new quantity of the product
     * @param expiryDate the new expiry date of the product in YYYY-MM-DD format
     * @param typeId     the new ID of the product type (foreign key to the ProductType table)
     * @throws SQLException if a database access error occurs
     */
    public void updateProduct(int productId, String name, int quantity, String expiryDate, int typeId) throws SQLException {
        String sql = "UPDATE Product SET name = ?, quantity = ?, expiry_date = ?, type_id = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, expiryDate);
            stmt.setInt(4, typeId);
            stmt.setInt(5, productId);
            stmt.executeUpdate();
        }
    }
}
