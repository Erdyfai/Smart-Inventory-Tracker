package com.uap.smartinventorytracker.util;

import java.util.List;

import com.uap.smartinventorytracker.model.ProductType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryUtil {
	
	public static int getProductTypeIdByName(String categoryName, List<ProductType> productTypeData) {
        for (ProductType productType : productTypeData) {
            if (productType.getName().equalsIgnoreCase(categoryName)) {
                return productType.getId(); 
            }
        }
        return -1; 
    }
	
	public static ObservableList<String> getAllProductTypeNames(List<ProductType> productTypes) {
        ObservableList<String> typeNames = FXCollections.observableArrayList();
        for (ProductType type : productTypes) {
            typeNames.add(type.getName());
        }
        return typeNames;
    }
	
	public static String getProductTypeNameById(int typeId, List<ProductType> productTypes) {
        return productTypes.stream()
                .filter(type -> type.getId() == typeId)
                .map(ProductType::getName)
                .findFirst()
                .orElse("Unknown");
	}
	  
}
