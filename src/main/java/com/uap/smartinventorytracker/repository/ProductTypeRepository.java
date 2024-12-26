package com.uap.smartinventorytracker.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.uap.smartinventorytracker.database.Database;
import com.uap.smartinventorytracker.model.ProductType;

/**
 * Repository class for managing product types in the inventory.
 * Provides methods to add, delete, update, and list all product types.
 */
public class ProductTypeRepository {
	
	/**
	 * Retrieves a list of all product types from the database.
	 * 
	 * @return a list of ProductType objects or an empty list if no product types are found
	 * @throws SQLException if a database access error occurs
	 */
	public List<ProductType> getAllProductTypes() throws SQLException {
	    List<ProductType> productTypes = new ArrayList<>();
	    String sql = "SELECT id, name FROM ProductType";

	    try (Connection conn = Database.connect();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            ProductType productType = new ProductType(id, name);
	            productTypes.add(productType);
	        }
	    }

	    return productTypes;  // Return an empty list if no product types found
	}


    /**
     * Adds a new product type to the database.
     *
     * @param name the name of the product type
     * @throws SQLException if a database access error occurs
     */
    public void addProductType(String name) throws SQLException {
        String sql = "INSERT INTO ProductType (name) VALUES (?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a product type from the database by ID.
     *
     * @param id the ID of the product type to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteProductType(int id) throws SQLException {
        String sql = "DELETE FROM ProductType WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No product type found with ID: " + id);
            } else {
                System.out.println("Product type deleted successfully.");
            }
        }
    }

    /**
     * Updates the name of an existing product type in the database.
     *
     * @param id the ID of the product type to update
     * @param newName the new name for the product type
     * @throws SQLException if a database access error occurs
     */
    public void updateProductType(int id, String newName) throws SQLException {
        String sql = "UPDATE ProductType SET name = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();  // Add this line to actually update the database
        }
    }

    /**
     * Retrieves a list of product types from the database, including their ID and name.
     * 
     * @return A formatted string containing the ID and name of each product type, 
     *         or an empty string if no product types are found.
     * 
     * @throws SQLException if a database access error occurs or the query fails.
     */
    public String listProductTypes() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT id, name FROM ProductType";

        try (Connection conn = Database.connect(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Name: ").append(rs.getString("name"))
                      .append("\n");  
            }
        }

        return result.toString().trim();
    }


}
