package com.uap.smartinventorytracker.controller;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.model.ProductType;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.repository.ProductTypeRepository;
import com.uap.smartinventorytracker.util.AlertUtil;
import com.uap.smartinventorytracker.util.CategoryUtil;
import com.uap.smartinventorytracker.util.SortUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.beans.property.ReadOnlyStringWrapper;

public class RestockController {

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private ComboBox<String> filterJumlah;

    @FXML
    private Button restockButton;

    @FXML
    private TableColumn<Product, Integer> jumlahColumn;

    @FXML
    private TableColumn<Product, String> namaColumn;

    @FXML
    private TableView<Product> restockTable;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Product, String> statusColumn;

    private final ProductRepository productRepo = new ProductRepository();
    private final ProductTypeRepository productTypeRepo = new ProductTypeRepository();

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

        namaColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        jumlahColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        categoryColumn.setCellValueFactory(cellData -> {
            int typeId = cellData.getValue().getTypeId();
            String typeName = CategoryUtil.getProductTypeNameById(typeId, productTypeData);
            return new ReadOnlyStringWrapper(typeName);
        });

        statusColumn.setCellValueFactory(cellData -> {
            int quantity = cellData.getValue().getQuantity();
            if (quantity == 0) {
                return new ReadOnlyStringWrapper("Out of Stock");
            } else if (quantity < 10) {
                return new ReadOnlyStringWrapper("Low Stock");
            } else {
                return new ReadOnlyStringWrapper("In Stock");
            }
        });

        restockTable.setItems(productData);

        // Filter products based on quantity
        filterJumlah.setItems(FXCollections.observableArrayList("All", "Low Stock", "In Stock"));
        filterJumlah.getSelectionModel().select("All");
        filterJumlah.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("Low Stock") || newVal.equals("In Stock")) {
                SortUtil.filterByQuantity(productData, restockTable, newVal);
            } else {
                restockTable.setItems(productData);
            }
        });

        search.textProperty().addListener((obs, oldVal, newVal) -> SortUtil.filterBySearch(productData, newVal, restockTable));

        restockButton.setOnAction(event -> restockSelectedProduct());
    }

    @FXML
    private void restockSelectedProduct() {
        Product selectedProduct = restockTable.getSelectionModel().getSelectedItem();
        
        // Check if a product is selected
        if (selectedProduct != null) {
            // Proceed with restocking the selected product
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uap/smartinventorytracker/view/restock_input.fxml"));
                VBox root = loader.load();

                RestockInputController restockInputController = loader.getController();
                restockInputController.initialize(selectedProduct);
                restockInputController.setRestockController(this);

                Stage addProductStage = new Stage();
                addProductStage.setTitle("Tambah Produk");

                Scene scene = new Scene(root);
                addProductStage.setScene(scene);

                addProductStage.setResizable(false);
                addProductStage.setWidth(400);
                addProductStage.setHeight(300);

                addProductStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	AlertUtil.showWarning("Invalid", "Please select a product before restocking.");
        }
    }
    
    public void refreshProductTable() {
        productData = FXCollections.observableArrayList(productRepo.getAllProducts());
		restockTable.setItems(productData);
    }


}
