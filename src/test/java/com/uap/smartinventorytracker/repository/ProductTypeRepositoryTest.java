package com.uap.smartinventorytracker.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uap.smartinventorytracker.database.Database;

class ProductTypeRepositoryTest {

    private Connection connection;
    private ProductTypeRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Database.connect(); 
        repository = new ProductTypeRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM ProductType");  
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='ProductType'");
        }
        connection.close(); 
    }

    @Test
    void testAddProductType() throws SQLException {
        repository.addProductType("Electronics");

        String sql = "SELECT * FROM ProductType WHERE name = 'Electronics'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("id"));
            assertEquals("Electronics", rs.getString("name"));
        }
    }

    @Test
    void testDeleteProductType() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (name) VALUES ('Electronics')");
        }

        repository.deleteProductType(1); 

        String sql = "SELECT * FROM Product WHERE id = 1";
        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            assertFalse(rs.next());
        }
    }

    @Test
    void testUpdateProductType() throws SQLException {        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (name) VALUES ('Furniture')");
        }
        
        repository.updateProductType(1, "Home Decor");

        String sql = "SELECT * FROM ProductType WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            assertTrue(rs.next());
            assertEquals("Home Decor", rs.getString("name"));
        }

    }

    @Test
    void testListProductTypes() throws SQLException {
    	 try (Statement stmt = connection.createStatement()) {
             stmt.execute("INSERT INTO ProductType (name) VALUES ('Furniture')");             
         }
    	assertEquals("ID: 1, Name: Furniture", repository.listProductTypes()); 
    }
}
