package com.boiko.taisa.salon.dal.mapper;

import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;
import com.boiko.taisa.salon.domain.entity.Visit;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseProductUsageRecordMapper implements FirebaseEntityMapper<ProductUsageRecord> {
    @Override
    public List<ProductUsageRecord> mapCollection(DataSnapshot snapshot) {
        return null;
    }

    @Override
    public ProductUsageRecord mapItem(DataSnapshot snapshot) {
        return new ProductUsageRecord();
    }

    @Override
    public Map<String, Object> mapToFirebaseObject(ProductUsageRecord item) {
        HashMap<String, Object> record = new HashMap<>();
        record.put("productId", item.getProductId());
        record.put("amount", item.getAmount());
        return record;
    }
}
