package com.uap.smartinventorytracker.controller;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.model.ProductType;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.repository.ProductTransactionRepository;
import com.uap.smartinventorytracker.repository.ProductTypeRepository;
import com.uap.smartinventorytracker.util.AlertUtil;
import com.uap.smartinventorytracker.util.CategoryUtil;
import com.uap.smartinventorytracker.util.SortUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyStringWrapper;

public class ExpiryAlertsController {

    @FXML
    private TableColumn<Product, String> category_column;

    @FXML
    private ComboBox<String> expiry_filter;

    @FXML
    private Button deleteProduct_button;

    @FXML
    private TableColumn<Product, LocalDate> expiry_column;

    @FXML
    private TableView<Product> expiry_table;

    @FXML
    private TableColumn<Product, Integer> jumlah_column;

    @FXML
    private TableColumn<Product, String> nama_column;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Product, String> status_column;

    private final ProductRepository productRepo = new ProductRepository();
    private final ProductTypeRepository productTypeRepo = new ProductTypeRepository();
    private final ProductTransactionRepository productTransactionRepo = new ProductTransactionRepository();

    private ObservableList<Product> productData;
    private ObservableList<ProductType> productTypeData;

    @FXML
    public void initialize() {
        try {
            productData = FXCollections.observableArrayList(productRepo.getAllProducts());
			productTypeData = FXCollections.observableArrayList(productTypeRepo.getAllProductTypes());
		} catch (SQLException e) {
			e.printStackTrace();
		}

        nama_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        jumlah_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expiry_column.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        category_column.setCellValueFactory(cellData -> {
            int typeId = cellData.getValue().getTypeId();
            String typeName = CategoryUtil.getProductTypeNameById(typeId, productTypeData);
            return new ReadOnlyStringWrapper(typeName);
        });

        status_column.setCellValueFactory(cellData -> {
            LocalDate expiryDate = cellData.getValue().getExpiryDate();
            if (expiryDate == null) return new ReadOnlyStringWrapper("Unknown");
            if (expiryDate.isBefore(LocalDate.now())) {
                return new ReadOnlyStringWrapper("Expired");
            } else if (expiryDate.isBefore(LocalDate.now().plusDays(7))) {
                return new ReadOnlyStringWrapper("Expiring Soon");
            } else {
                return new ReadOnlyStringWrapper("Valid");
            }
        });
        
        expiry_table.setItems(productData);

        expiry_filter.setItems(FXCollections.observableArrayList("All", "Expired Closest", "Expired Farthest"));
        expiry_filter.getSelectionModel().select("All");
        expiry_filter.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("Expired Closest") || newVal.equals("Expired Farthest")) {
                SortUtil.filterByExpiry(productData, expiry_table, newVal);
            } else {
                expiry_table.setItems(productData);
            }
        });
        search.textProperty().addListener((obs, oldVal, newVal) -> SortUtil.filterBySearch(productData, newVal, expiry_table));

        deleteProduct_button.setOnAction(event -> deleteSelectedProduct());
    }

    private void deleteSelectedProduct() {
        Product selectedProduct = expiry_table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean confirmation = AlertUtil.showConfirmation(
                "Delete Confirmation", 
                "Are you sure you want to delete the selected product?"
            );

            if (confirmation) {
                productData.remove(selectedProduct);
                try {
                    productRepo.deleteProduct(selectedProduct.getId());
                    productTransactionRepo.addTransaction(selectedProduct.getId(), "REMOVE", selectedProduct.getQuantity());
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, 
                            "Failed to delete the product from the database.", 
                            ButtonType.OK);
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, 
                    "No product selected for deletion.", ButtonType.OK);
            alert.showAndWait();
        }
    }

}
