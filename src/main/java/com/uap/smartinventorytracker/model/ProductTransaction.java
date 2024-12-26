package com.uap.smartinventorytracker.model;

import java.time.LocalDateTime;

import com.uap.smartinventorytracker.util.DateUtil;

public class ProductTransaction {
    private int id;
    private int productId;
    private String transactionType; // 'ADD' or 'REMOVE'
    private int quantity;
    private LocalDateTime transactionDate; // Menggunakan LocalDateTime untuk tanggal dan waktu
    
    public ProductTransaction() {}

    public ProductTransaction(int productId, String transactionType, int quantity, LocalDateTime transactionDate) {
        this.productId = productId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public String toString() {
        return "ProductTransaction{" +
                "id=" + id +
                ", productId=" + productId +
                ", transactionType='" + transactionType + '\'' +
                ", quantity=" + quantity +
                ", transactionDate=" + DateUtil.formatDateTime(transactionDate) + // Memformat tanggal dan waktu
                '}';
    }

    public String getFormattedTransactionDate() {
        return DateUtil.formatDateTime(transactionDate); // Menggunakan DateUtil untuk format yang konsisten
    }
}
