package com.uap.smartinventorytracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private Button restock_button;

    @FXML
    private Button dashboard_button;

    @FXML
    private Button expiry_button;

    @FXML
    private Button inventory_button;

    @FXML
    private AnchorPane menu;

    @FXML
    public void initialize() {
        dashboard_button.setOnAction(event -> loadView("DashboardView.fxml"));
        restock_button.setOnAction(event -> loadView("RestockView.fxml"));
        expiry_button.setOnAction(event -> loadView("ExpiryAlertsView.fxml"));
        inventory_button.setOnAction(event -> loadView("InventoryView.fxml"));
        
        loadView("DashboardView.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/uap/smartinventorytracker/view/" + fxmlFile));
            AnchorPane newView = loader.load();

            menu.getChildren().clear();
            menu.getChildren().add(newView);
            
            
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load " + fxmlFile);
        }
    }
}
