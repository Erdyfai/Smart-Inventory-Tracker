package com.uap.smartinventorytracker.util;

import com.uap.smartinventorytracker.model.ProductType;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryUtilTest {

    // Mock data
    private final List<ProductType> productTypeData = Arrays.asList(
            new ProductType(1, "Electronics"),
            new ProductType(2, "Furniture"),
            new ProductType(3, "Clothing")
    );

    @Test
    void testGetProductTypeIdByName_Found() {
        int id = CategoryUtil.getProductTypeIdByName("Electronics", productTypeData);
        assertEquals(1, id);

        id = CategoryUtil.getProductTypeIdByName("FURNITURE", productTypeData); // Test case-insensitive
        assertEquals(2, id);
    }

    @Test
    void testGetProductTypeIdByName_NotFound() {
        int id = CategoryUtil.getProductTypeIdByName("Toys", productTypeData);
        assertEquals(-1, id);
    }

    @Test
    void testGetAllProductTypeNames() {
        ObservableList<String> typeNames = CategoryUtil.getAllProductTypeNames(productTypeData);
        assertNotNull(typeNames);
        assertEquals(3, typeNames.size());
        assertTrue(typeNames.containsAll(Arrays.asList("Electronics", "Furniture", "Clothing")));
    }

    @Test
    void testGetProductTypeNameById_Found() {
        String name = CategoryUtil.getProductTypeNameById(1, productTypeData);
        assertEquals("Electronics", name);

        name = CategoryUtil.getProductTypeNameById(3, productTypeData);
        assertEquals("Clothing", name);
    }

    @Test
    void testGetProductTypeNameById_NotFound() {
        String name = CategoryUtil.getProductTypeNameById(99, productTypeData);
        assertEquals("Unknown", name);
    }
}
