/**
 * Utility class for displaying various types of alerts in a JavaFX application.
 */
package com.uap.smartinventorytracker.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertUtil {

    /**
     * Displays an informational alert with the given title and content.
     *
     * @param title   the title of the alert dialog
     * @param content the content text of the alert dialog
     */
    public static void showInfo(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);  
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert with the given title and content.
     *
     * @param title   the title of the alert dialog
     * @param content the content text of the alert dialog
     */
    public static void showWarning(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays an error alert with the given title and content.
     *
     * @param title   the title of the alert dialog
     * @param content the content text of the alert dialog
     */
    public static void showError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation alert with the given title and content,
     * and returns the user's response.
     *
     * @param title   the title of the alert dialog
     * @param content the content text of the alert dialog
     * @return {@code true} if the user clicked "OK", {@code false} otherwise
     */
    public static boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait();

        return alert.getResult() == okButton; 
    }
}
