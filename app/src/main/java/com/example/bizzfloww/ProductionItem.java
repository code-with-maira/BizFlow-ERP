package com.example.bizzfloww;

public class ProductionItem {
    private String id;
    private String productName;
    private int productionQuantity;
    private int wastageQuantity;
    private String productionDate;

    public ProductionItem(String id, String productName, int productionQuantity, int wastageQuantity, String productionDate) {
        this.id = id;
        this.productName = productName;
        this.productionQuantity = productionQuantity;
        this.wastageQuantity = wastageQuantity;
        this.productionDate = productionDate;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductionQuantity() {
        return productionQuantity;
    }

    public int getWastageQuantity() {
        return wastageQuantity;
    }

    public String getProductionDate() {
        return productionDate;
    }
}
