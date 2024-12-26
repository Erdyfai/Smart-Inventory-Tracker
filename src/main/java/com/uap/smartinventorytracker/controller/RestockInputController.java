package com.uap.smartinventorytracker.controller;

import java.sql.SQLException;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.util.AlertUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RestockInputController {

    @FXML
    private TextField restockQuantityField;

    private Product selectedProduct;
    private ProductRepository productRepo = new ProductRepository();
    RestockController restockController;

    public void initialize(Product product) {
        this.selectedProduct = product;
    }
    
    public void setRestockController(RestockController restockController) {
        this.restockController = restockController;
    }

    @FXML
    public void handleRestock() {
        try {
            int restockQuantity = Integer.parseInt(restockQuantityField.getText());
            if (restockQuantity <= 0) {
                AlertUtil.showError("Jumlah harus lebih besar dari 0.", "Input invalid");
                return;
            }

            selectedProduct.setQuantity(selectedProduct.getQuantity() + restockQuantity);
          
			productRepo.updateProduct(selectedProduct);
		

            AlertUtil.showInfo("Product restocked successfully!", "Success");
            restockController.refreshProductTable();
            closeWindow();
        } catch (NumberFormatException e) {
        	AlertUtil.showError("Please enter a valid number.", "Invalid");
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void handleCancel() {
        closeWindow();
    }
    private void closeWindow() {
        Stage stage = (Stage) restockQuantityField.getScene().getWindow();
        stage.close();
    }
}
