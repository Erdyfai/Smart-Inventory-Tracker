package com.uap.smartinventorytracker.model;

import java.time.LocalDate;

import com.uap.smartinventorytracker.util.DateUtil;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private LocalDate expiryDate; // Menggunakan LocalDate untuk tanggal
    private int typeId;

    public Product() {}

    public Product(String name, int quantity, LocalDate expiryDate, int typeId) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.typeId = typeId;
    }
    
    public Product(int id, String name, int quantity, LocalDate expiryDate, int typeId) {
    	this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.typeId = typeId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public int getTypeId() { return typeId; }
    public void setTypeId(int typeId) { this.typeId = typeId; }

    // Method untuk mengembalikan expiryDate dalam format String sesuai dengan database
    public String getFormattedExpiryDate() {
        return DateUtil.format(expiryDate); // Format LocalDate ke String menggunakan DateUtil
    }

    // Method untuk set expiryDate dari String yang didapat dari database
    public void setExpiryDateFromString(String expiryDateString) {
        this.expiryDate = DateUtil.parse(expiryDateString); // Parse String ke LocalDate menggunakan DateUtil
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                ", typeId=" + typeId +
                '}';
    }
}
