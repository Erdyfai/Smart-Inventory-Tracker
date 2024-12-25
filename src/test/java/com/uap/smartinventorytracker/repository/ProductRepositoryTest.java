package com.uap.smartinventorytracker.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uap.smartinventorytracker.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ProductRepositoryTest {

    private Connection connection;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Database.connect();
        productRepository = new ProductRepository();
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Product");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Product'");
            stmt.execute("DELETE FROM ProductType");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='ProductType'");
        }
        connection.close();
    }
    
    @Test
    void testAddProduct() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (name) VALUES ('Electronics')");
        }

        productRepository.addProduct("Laptop", 10, "2025-12-31", 1);

        String sql = "SELECT * FROM Product WHERE name = 'Laptop'";
        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            assertTrue(rs.next());
            assertEquals("Laptop", rs.getString("name"));
            assertEquals(10, rs.getInt("quantity"));
            assertEquals("2025-12-31", rs.getString("expiry_date"));
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Product WHERE name = 'Laptop'");
        }
    }

    @Test
    void testListProducts() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (name) VALUES ('Electronics')");
            stmt.execute("INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES ('Laptop', 10, '2025-12-31', 1)");
            stmt.execute("INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES ('Smartphone', 20, '2025-06-30', 1)");
        }

        try (var stmt = connection.prepareStatement("SELECT COUNT(*) FROM Product")) {
            try (var rs = stmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(2, rs.getInt(1), "There should be 2 products in the database");
            }
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Product WHERE name IN ('Laptop', 'Smartphone')");
            stmt.execute("DELETE FROM ProductType WHERE name = 'Electronics'");
        }
    }


    @Test
    void testDeleteProduct() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (name) VALUES ('Electronics')");
            stmt.execute("INSERT INTO Product (name, quantity, expiry_date, type_id) VALUES ('Laptop', 10, '2025-12-31', 1)");
        }

        productRepository.deleteProduct(1); 

        String sql = "SELECT * FROM Product WHERE id = 1";
        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            assertFalse(rs.next());
        }
    }

    @Test
    void testUpdateProduct() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductType (id, name) VALUES (1, 'Electronics')");
            stmt.execute("INSERT INTO Product (id, name, quantity, expiry_date, type_id) VALUES (1, 'Laptop', 10, '2025-12-31', 1)");
        }

        productRepository.updateProduct(1, "Laptop Pro", 15, "2026-01-01", 1);

        String sql = "SELECT * FROM Product WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            assertTrue(rs.next());
            assertEquals("Laptop Pro", rs.getString("name"));
            assertEquals(15, rs.getInt("quantity"));
            assertEquals("2026-01-01", rs.getString("expiry_date"));
            assertEquals(1, rs.getInt("type_id"));
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Product WHERE id = 1");
            stmt.execute("DELETE FROM ProductType WHERE id = 1");
        }
    }

}
