package com.uap.smartinventorytracker.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Memformat LocalDateTime menjadi string dengan format yang sudah ditentukan.
     *
     * @param dateTime LocalDateTime yang akan diformat
     * @return String tanggal/waktu dalam format "yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.format(formatter);
        }
        return null;  
    }

    /**
     * Parse sebuah tanggal dari String ke LocalDate.
     * @param dateString Tanggal dalam format "yyyy-MM-dd".
     * @return LocalDate hasil parsing, atau null jika parsing gagal.
     */
    public static LocalDate parse(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateString + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Format sebuah LocalDate menjadi String.
     * @param date Objek LocalDate yang akan diformat.
     * @return String hasil format dalam format "yyyy-MM-dd", atau null jika date adalah null.
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DEFAULT_FORMATTER);
    }

    /**
     * Validasi apakah sebuah string adalah tanggal valid dengan format "yyyy-MM-dd".
     * @param dateString Tanggal dalam format string.
     * @return True jika valid, false jika tidak.
     */
    public static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DEFAULT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
