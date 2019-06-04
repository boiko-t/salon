package com.boiko.taisa.salon.domain.entity;

public class Product {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private String unit;
    private int price;

    public Product(String id, String name, String description, String categoryId, String unit, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.unit = unit;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
