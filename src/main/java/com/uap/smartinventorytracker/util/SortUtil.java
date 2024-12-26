package com.uap.smartinventorytracker.util;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.model.ProductType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.Comparator;
import java.util.List;

public class SortUtil {

    public static void filterByCategory(ObservableList<Product> productData, String category, TableView<Product> table, List<ProductType> productTypes) {
        if (category == null || category.equals("All")) {
            table.setItems(productData);
            return;
        }

        ObservableList<Product> filteredData = FXCollections.observableArrayList();
        int categoryTypeId = getCategoryTypeId(category, productTypes);

        for (Product product : productData) {
            if (product.getTypeId() == categoryTypeId) {
                filteredData.add(product);
            }
        }
        table.setItems(filteredData);
    }

    public static void filterBySearch(ObservableList<Product> productData, String keyword, TableView<Product> table) {
        if (keyword == null || keyword.isEmpty()) {
            table.setItems(productData);
            return;
        }

        ObservableList<Product> filteredData = FXCollections.observableArrayList();
        for (Product product : productData) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredData.add(product);
            }
        }
        table.setItems(filteredData);
    }

    private static int getCategoryTypeId(String category, List<ProductType> productTypes) {
        return productTypes.stream()
                .filter(type -> type.getName().equalsIgnoreCase(category))
                .map(ProductType::getId)
                .findFirst()
                .orElse(-1);
    }
    
    public static void filterByQuantity(ObservableList<Product> productData, TableView<Product> table, String quantityFilter) {
        if (productData == null || productData.isEmpty()) {
            return;
        }

        ObservableList<Product> sortedData = FXCollections.observableArrayList(productData);

        switch (quantityFilter) {
            case "Min Quantity":
                // Mengurutkan produk berdasarkan quantity (dari yang paling sedikit)
                sortedData.sort(Comparator.comparing(Product::getQuantity));
                break;
            case "Max Quantity":
                // Mengurutkan produk berdasarkan quantity (dari yang paling banyak)
                sortedData.sort(Comparator.comparing(Product::getQuantity).reversed());
                break;
            default:
                // Jika "All", kembalikan ke data semula
                sortedData = FXCollections.observableArrayList(productData);
                break;
        }

        table.setItems(sortedData);
    }

    // Method untuk filter berdasarkan expiry date (termasuk expired paling dekat dan paling jauh)
    public static void filterByExpiry(ObservableList<Product> productData, TableView<Product> table, String expiryFilter) {
        if (productData == null || productData.isEmpty()) {
            return;
        }

        ObservableList<Product> sortedData = FXCollections.observableArrayList(productData);

        switch (expiryFilter) {
            case "Expired Closest":
                // Mengurutkan produk berdasarkan expiryDate (dari yang paling mendekati)
                sortedData.sort(Comparator.comparing(Product::getExpiryDate));
                break;
            case "Expired Farthest":
                // Mengurutkan produk berdasarkan expiryDate (dari yang paling lama)
                sortedData.sort(Comparator.comparing(Product::getExpiryDate).reversed());
                break;
            default:
                // Jika "All", kembalikan ke data semula
                sortedData = FXCollections.observableArrayList(productData);
                break;
        }

        table.setItems(sortedData);
    }

}
