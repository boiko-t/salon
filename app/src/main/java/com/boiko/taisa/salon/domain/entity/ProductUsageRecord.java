package com.boiko.taisa.salon.domain.entity;

public class ProductUsageRecord {
    private String productId;
    private int amount;

    public ProductUsageRecord() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
