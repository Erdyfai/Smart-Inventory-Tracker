/**
 * Utility class for filtering and sorting product data in a JavaFX TableView.
 */
package com.uap.smartinventorytracker.util;

import com.uap.smartinventorytracker.model.Product;
import com.uap.smartinventorytracker.model.ProductType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.Comparator;
import java.util.List;

public class SortUtil {

    /**
     * Filters the product data by category and updates the given TableView with the filtered results.
     *
     * @param productData the original list of products
     * @param category    the category name to filter by
     * @param table       the TableView to update with the filtered results
     * @param productTypes the list of {@link ProductType} objects to match category names to IDs
     */
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

    /**
     * Filters the product data by a keyword search and updates the given TableView with the filtered results.
     *
     * @param productData the original list of products
     * @param keyword     the keyword to search for in product names
     * @param table       the TableView to update with the filtered results
     */
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

    /**
     * Retrieves the ID of a product type based on its name.
     *
     * @param category     the name of the product type
     * @param productTypes the list of {@link ProductType} objects
     * @return the ID of the product type if found; otherwise, -1
     */
    private static int getCategoryTypeId(String category, List<ProductType> productTypes) {
        return productTypes.stream()
                .filter(type -> type.getName().equalsIgnoreCase(category))
                .map(ProductType::getId)
                .findFirst()
                .orElse(-1);
    }

    /**
     * Sorts the product data by quantity and updates the given TableView.
     *
     * @param productData   the original list of products
     * @param table         the TableView to update with the sorted results
     * @param quantityFilter the filter to determine the sorting order ("Low Stock" or "In Stock")
     */
    public static void filterByQuantity(ObservableList<Product> productData, TableView<Product> table, String quantityFilter) {
        if (productData == null || productData.isEmpty()) {
            return;
        }

        ObservableList<Product> sortedData = FXCollections.observableArrayList(productData);

        switch (quantityFilter) {
            case "Low Stock":
                sortedData.sort(Comparator.comparing(Product::getQuantity));
                break;
            case "In Stock":
                sortedData.sort(Comparator.comparing(Product::getQuantity).reversed());
                break;
            default:
                sortedData = FXCollections.observableArrayList(productData);
                break;
        }

        table.setItems(sortedData);
    }

    /**
     * Sorts the product data by expiry date and updates the given TableView.
     *
     * @param productData  the original list of products
     * @param table        the TableView to update with the sorted results
     * @param expiryFilter the filter to determine the sorting order ("Expired Closest" or "Expired Farthest")
     */
    public static void filterByExpiry(ObservableList<Product> productData, TableView<Product> table, String expiryFilter) {
        if (productData == null || productData.isEmpty()) {
            return;
        }

        ObservableList<Product> sortedData = FXCollections.observableArrayList(productData);

        switch (expiryFilter) {
            case "Expired Closest":
                sortedData.sort(Comparator.comparing(Product::getExpiryDate));
                break;
            case "Expired Farthest":
                sortedData.sort(Comparator.comparing(Product::getExpiryDate).reversed());
                break;
            default:
                sortedData = FXCollections.observableArrayList(productData);
                break;
        }

        table.setItems(sortedData);
    }
}
