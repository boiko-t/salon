package com.boiko.taisa.salon.dal.mapper;

import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;
import com.boiko.taisa.salon.domain.entity.Visit;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseVisitMapper implements FirebaseEntityMapper<Visit> {
    @Override
    public List<Visit> mapCollection(DataSnapshot snapshot) {
        return null;
    }

    @Override
    public Visit mapItem(DataSnapshot snapshot) {
        return new Visit();
    }

    @Override
    public Map<String, Object> mapToFirebaseObject(Visit item) {
        HashMap<String, Object> visit = new HashMap<>();
        HashMap<String, Object> products = new HashMap<>();
        for(ProductUsageRecord product : item.getProducts()) {
            products.put(product.getProductId(), product.getAmount());
        }
        visit.put("masterName", item.getMasterName());
        visit.put("clientName", item.getClientName());
        visit.put("service", item.getService().getId());
        visit.put("date", item.getDate());
        visit.put("price", item.getPrice());
        visit.put("products", products);
        return visit;
    }
}
