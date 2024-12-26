package com.uap.smartinventorytracker.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uap.smartinventorytracker.database.Database;
import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.util.DateUtil;

/**
 * Repository class for managing products in the inventory.
 * Provides methods to add new products, list all existing products, update, and delete products.
 */
public class ProductRepository {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, quantity, expiry_date, type_id FROM Product";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                String expiryDateString = rs.getString("expiry_date"); 
                LocalDate expiryDate = DateUtil.parse(expiryDateString); 
                int typeId = rs.getInt("type_id");

                Product product = new Product(id, name, quantity, expiryDate, typeId);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public int getTotalProductQuantity() {
        String query = "SELECT SUM(quantity) AS total FROM Product";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setString(3, DateUtil.format(product.getExpiryDate())); 
            stmt.setInt(4, product.getTypeId());
            stmt.executeUpdate();
        }
    }

    public void addProduct(String name, int quantity, LocalDate expiryDate, int typeId) throws SQLException {
        String sql = "INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, DateUtil.format(expiryDate)); 
            stmt.setInt(4, typeId);
            stmt.executeUpdate();
        }
    }

    public String listProducts() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT p.id, p.name, p.quantity, p.expiry_date, t.name AS type_name " +
                     "FROM Product p JOIN ProductType t ON p.type_id = t.id";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String expiryDateString = rs.getString("expiry_date");
                LocalDate expiryDate = DateUtil.parse(expiryDateString); 

                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Name: ").append(rs.getString("name"))
                      .append(", Quantity: ").append(rs.getInt("quantity"))
                      .append(", Expiry Date: ").append(DateUtil.format(expiryDate)) 
                      .append(", Type: ").append(rs.getString("type_name"))
                      .append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }

    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM Product WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }
    
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Product SET name = ?, quantity = ?, expiry_date = ?, type_id = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setString(3, DateUtil.format(product.getExpiryDate())); 
            stmt.setInt(4, product.getTypeId());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        }
    }

    public void updateProduct(int productId, String name, int quantity, LocalDate expiryDate, int typeId) throws SQLException {
        String sql = "UPDATE Product SET name = ?, quantity = ?, expiry_date = ?, type_id = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setString(3, DateUtil.format(expiryDate)); 
            stmt.setInt(4, typeId);
            stmt.setInt(5, productId);
            stmt.executeUpdate();
        }
    }
}
