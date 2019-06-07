package com.boiko.taisa.salon.ui.event;

import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;

public interface RecyclerViewEventListener {
    void onRecycleItemRemoved(ProductUsageRecord recyclerPosition);
    void onRecycleItemTextChanged(String text, ProductUsageRecord recyclerPosition);
    void onRecycleItemSpinnerProductSelected(String productId, ProductUsageRecord recyclerPosition);
}
