/**
 * Utility class for operations related to product categories in the inventory system.
 */
package com.uap.smartinventorytracker.util;

import java.util.List;

import com.uap.smartinventorytracker.model.ProductType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryUtil {

    /**
     * Retrieves the ID of a product type based on its name.
     *
     * @param categoryName   the name of the product type
     * @param productTypeData the list of {@link ProductType} objects to search
     * @return the ID of the product type if found; otherwise, returns -1
     */
    public static int getProductTypeIdByName(String categoryName, List<ProductType> productTypeData) {
        for (ProductType productType : productTypeData) {
            if (productType.getName().equalsIgnoreCase(categoryName)) {
                return productType.getId(); 
            }
        }
        return -1; 
    }

    /**
     * Retrieves an observable list of all product type names from a list of {@link ProductType}.
     *
     * @param productTypes the list of {@link ProductType} objects
     * @return an {@link ObservableList} of product type names
     */
    public static ObservableList<String> getAllProductTypeNames(List<ProductType> productTypes) {
        ObservableList<String> typeNames = FXCollections.observableArrayList();
        for (ProductType type : productTypes) {
            typeNames.add(type.getName());
        }
        return typeNames;
    }

    /**
     * Retrieves the name of a product type based on its ID.
     *
     * @param typeId       the ID of the product type
     * @param productTypes the list of {@link ProductType} objects
     * @return the name of the product type if found; otherwise, returns "Unknown"
     */
    public static String getProductTypeNameById(int typeId, List<ProductType> productTypes) {
        return productTypes.stream()
                .filter(type -> type.getId() == typeId)
                .map(ProductType::getName)
                .findFirst()
                .orElse("Unknown");
    }
}
