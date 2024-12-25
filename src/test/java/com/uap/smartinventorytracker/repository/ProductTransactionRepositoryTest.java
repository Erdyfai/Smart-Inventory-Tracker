package com.uap.smartinventorytracker.repository;

import org.junit.jupiter.api.*;

import com.uap.smartinventorytracker.database.Database;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductTransactionRepositoryTest {

    private static Connection connection;
    private ProductTransactionRepository productTransactionRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Database.connect();
        productTransactionRepository = new ProductTransactionRepository();
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM ProductTransaction");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='ProductTransaction'");
        }
        connection.close();
    }

    @Test
    void testAddTransaction() throws SQLException {
        productTransactionRepository.addTransaction(1, "ADD", 10);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ProductTransaction WHERE product_id = 1 AND transaction_type = 'ADD' AND quantity = 10")) {
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("product_id"));
            assertEquals("ADD", rs.getString("transaction_type"));
            assertEquals(10, rs.getInt("quantity"));
        }
    }

    @Test
    void testListTransactions() throws SQLException {
        productTransactionRepository.addTransaction(1, "ADD", 10);
        productTransactionRepository.addTransaction(2, "REMOVE", 5);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM ProductTransaction")) {
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(2, rs.getInt(1), "There should be 2 transactions in the database");
        }
    }
    
    @Test
    void testUpdateProductTransaction() throws SQLException {        
        try (Statement stmt = connection.createStatement()) {
        	stmt.execute("INSERT INTO ProductTransaction (product_id, transaction_type, quantity) VALUES (1, 'ADD', 10)");
        }
        
        productTransactionRepository.updateTransaction(1, "REMOVE", 15);

        String sql = "SELECT * FROM ProductTransaction WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            assertTrue(rs.next());
            assertEquals("REMOVE", rs.getString("transaction_type"));
            assertEquals(15, rs.getInt("quantity"));
        }

    }

    
    @Test
    void testDeleteTransaction() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ProductTransaction (product_id, transaction_type, quantity) VALUES (1, 'ADD', 10)");
        }

        productTransactionRepository.deleteTransaction(1);; 

        String sql = "SELECT * FROM ProductTransaction WHERE id = 1";
        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            assertFalse(rs.next());
        }
    }

}
