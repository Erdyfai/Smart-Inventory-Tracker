package com.uap.smartinventorytracker.model;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private LocalDate expiryDate; // Menggunakan LocalDate untuk tanggal
    private int typeId;
    
    public Product() {}

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
