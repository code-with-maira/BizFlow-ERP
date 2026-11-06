package com.example.bizzfloww;

public class InventoryItem {
    private String id;
    private String name;
    private String productId;
    private String barcode;
    private int quantity;

    public InventoryItem(String id, String name, String productId, String barcode, int quantity) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
}
