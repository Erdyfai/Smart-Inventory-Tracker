package com.uap.smartinventorytracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.repository.ProductTransactionRepository;
import com.uap.smartinventorytracker.repository.ProductTypeRepository;
import com.uap.smartinventorytracker.util.AlertUtil;
import com.uap.smartinventorytracker.util.CategoryUtil;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddProductController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private DatePicker expiryDatePicker;

    private final ProductRepository productRepo = new ProductRepository();
    
    private final ProductTypeRepository productTypeRepo = new ProductTypeRepository();
    
    private final ProductTransactionRepository productTransactionRepo = new ProductTransactionRepository();
    
    private InventoryController inventoryController;

    public AddProductController() {
    }
    
    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    @FXML
    public void initialize() {
        try {
			categoryComboBox.setItems(CategoryUtil.getAllProductTypeNames(productTypeRepo.getAllProductTypes()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    private void onAddProductClick() {
        String productName = productNameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        String category = categoryComboBox.getValue();
        LocalDate expiryDate = expiryDatePicker.getValue();

        if (productName.isEmpty() || quantity <= 0 || category == null || expiryDate == null) {
            AlertUtil.showError("Input Tidak Valid", "Harap lengkapi semua data dengan benar.");
            return;
        }

        int typeId;
		try {
			typeId = CategoryUtil.getProductTypeIdByName(category, productTypeRepo.getAllProductTypes());
			Product newProduct = new Product(productName, quantity, expiryDate, typeId);
			 
			 productRepo.addProduct(newProduct); 
			 productTransactionRepo.addTransaction(typeId, "ADD", quantity);
			 inventoryController.refreshProductTable();
	         AlertUtil.showInfo("Berhasil", "Produk berhasil ditambahkan.");
		} catch (SQLException e) {
			 AlertUtil.showError("Kesalahan", "Gagal menambahkan produk.");
		}
    
    }
}
