package com.uap.smartinventorytracker.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void testFormatDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 14, 30, 45);
        String formatted = DateUtil.formatDateTime(dateTime);
        assertEquals("2024-12-25 14:30:45", formatted);

        assertNull(DateUtil.formatDateTime(null)); // Test null input
    }

    @Test
    void testParse_ValidDate() {
        String validDate = "2024-12-25";
        LocalDate parsedDate = DateUtil.parse(validDate);
        assertNotNull(parsedDate);
        assertEquals(LocalDate.of(2024, 12, 25), parsedDate);
    }

    @Test
    void testParse_InvalidDate() {
        String invalidDate = "2024-12-32"; // Invalid day
        assertNull(DateUtil.parse(invalidDate));

        String emptyDate = "   "; // Empty string
        assertNull(DateUtil.parse(emptyDate));

        String nullDate = null; // Null input
        assertNull(DateUtil.parse(nullDate));
    }

    @Test
    void testFormat_ValidDate() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        String formattedDate = DateUtil.format(date);
        assertEquals("2024-12-25", formattedDate);
    }

    @Test
    void testFormat_NullDate() {
        assertNull(DateUtil.format(null)); // Test null input
    }

    @Test
    void testIsValidDate_ValidDate() {
        assertTrue(DateUtil.isValidDate("2024-12-25")); // Valid date
    }
}
