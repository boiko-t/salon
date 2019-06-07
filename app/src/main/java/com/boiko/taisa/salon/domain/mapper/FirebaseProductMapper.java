package com.boiko.taisa.salon.domain.mapper;

import com.boiko.taisa.salon.domain.entity.Product;
import com.google.firebase.database.DataSnapshot;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FirebaseProductMapper implements FirebaseEntityMapper<Product> {
    @Override
    public List<Product> mapCollection(DataSnapshot snapshot) {
        return null;
    }

    @Override
    public Product mapItem(DataSnapshot snapshot) {
        String name = (String) snapshot.child("name").getValue();
        String description = (String) snapshot.child("description").getValue();
        String unit = (String) snapshot.child("unit").getValue();
        String categoryId = (String) snapshot.child("categoryId").getValue();
        int price = new BigDecimal((long)snapshot.child("price").getValue()).intValueExact();
        return new Product(snapshot.getKey(), name, description, categoryId, unit, price);
    }

    @Override
    public Map<String, Object> mapToFirebaseObject(Product item) {
        return null;
    }
}
