package com.boiko.taisa.salon.domain.entity;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.BinaryOperator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Visit {
    private String id;
    private String masterName;
    private String clientName;
    private SalonService service;
    private List<ProductUsageRecord> products;
    private Date date;
    private int price;

    public Visit() {
        date = new Date();
        products = new ArrayList<>();
    }

    public Visit(String id, String masterName, String clientName, SalonService service, List<ProductUsageRecord> products) {
        date = new Date();
        this.id = id;
        this.masterName = masterName;
        this.clientName = clientName;
        this.service = service;
        this.products = products;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public SalonService getService() {
        return service;
    }

    public void setService(SalonService service) {
        this.service = service;
    }

    public List<ProductUsageRecord> getProducts() {
        return products;
    }

    public void addProduct(ProductUsageRecord product) {
        products.add(product);
    }

    public void removeProduct(ProductUsageRecord product) {
        products.remove(product);
    }

    public void setProducts(List<ProductUsageRecord> products) {
        this.products = products;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return date.toString();
    }

    public int getPrice() {
        int productsPrice = Stream.of(products)
                .map(ProductUsageRecord::getPrice)
                .reduce(0, (BinaryOperator<Integer>) (previous, current) -> previous + current);

        return service.getPrice() + productsPrice;
    }
}
