package com.uap.smartinventorytracker.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseTest {

    @Test
    public void testConnect() {
        try (Connection conn = Database.connect()) {
            assertNotNull(conn, "Connection should not be null");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCloseConnection() {
        try (Connection conn = Database.connect()) {

            assertNotNull(conn, "Connection should not be null");

            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
