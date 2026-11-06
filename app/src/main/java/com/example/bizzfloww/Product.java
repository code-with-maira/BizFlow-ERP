package com.example.bizzfloww;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String productId;
    private String barcode;
    private int quantity;
    private String category;
    private boolean isVerified;

    public Product(String id, String name, String productId, String barcode, int quantity, String category, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.barcode = barcode;
        this.quantity = quantity;
        this.category = category;
        this.isVerified = isVerified;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
