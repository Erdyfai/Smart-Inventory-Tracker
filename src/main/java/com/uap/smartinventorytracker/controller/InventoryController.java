package com.uap.smartinventorytracker.controller;

import java.io.IOException;

import java.sql.SQLException;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.model.ProductType;
import com.uap.smartinventorytracker.repository.ProductRepository;
import com.uap.smartinventorytracker.repository.ProductTypeRepository;
import com.uap.smartinventorytracker.util.AlertUtil;
import com.uap.smartinventorytracker.util.CategoryUtil;
import com.uap.smartinventorytracker.util.SortUtil;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InventoryController {

    @FXML
    private Button addItem_button;

    @FXML
    private ComboBox<String> category_filter;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableView<Product> inventory_table;

    @FXML
    private TableColumn<Product, Integer> jumlahColumn;

    @FXML
    private TableColumn<Product, String> kategoriColumn;

    @FXML
    private TableColumn<Product, String> namaColumn;

    @FXML
    private TextField search;

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
    	
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        jumlahColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        kategoriColumn.setCellValueFactory(cellData -> {
            int typeId = cellData.getValue().getTypeId();
            String typeName = CategoryUtil.getProductTypeNameById(typeId, productTypeData);
            return new ReadOnlyStringWrapper(typeName);
        });

        inventory_table.setItems(productData);
        ObservableList<String> categories = FXCollections.observableArrayList(CategoryUtil.getAllProductTypeNames(productTypeData));
        categories.add(0, "All"); 
        
        category_filter.setItems(categories);
        category_filter.getSelectionModel().select("All");
        category_filter.valueProperty().addListener((obs, oldVal, newVal) -> SortUtil.filterByCategory(productData, newVal, inventory_table, productTypeData));

        search.textProperty().addListener((observable, oldValue, newValue) -> SortUtil.filterBySearch(productData, newValue, inventory_table));
    }
    

    @FXML
    private void onAddItemButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uap/smartinventorytracker/view/AddProduct.fxml"));
            AnchorPane root = loader.load();
            AddProductController controller = loader.getController();
            controller.setInventoryController(this);

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
            AlertUtil.showError("Error", "Failed to open add product window.");
        }
    }
    
    public void refreshProductTable() {
        productData = FXCollections.observableArrayList(productRepo.getAllProducts());
		inventory_table.setItems(productData);
    }

}
