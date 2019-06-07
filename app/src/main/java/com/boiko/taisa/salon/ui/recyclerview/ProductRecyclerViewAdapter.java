package com.boiko.taisa.salon.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.boiko.taisa.salon.R;
import com.boiko.taisa.salon.domain.entity.Product;
import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;
import com.boiko.taisa.salon.ui.adapter.ProductSpinnerAdapter;
import com.boiko.taisa.salon.ui.event.RecyclerViewEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    List<Product> productsCollection;
    private List<ProductUsageRecord> dataSet;
    private RecyclerViewEventListener listener;
    private ProductSpinnerAdapter productSpinnerAdapter;

    public ProductRecyclerViewAdapter(Context context, List<Product> productsCollection, List<ProductUsageRecord> dataSet) {
        this.dataSet = dataSet;
        this.productsCollection = productsCollection;
        productSpinnerAdapter = new ProductSpinnerAdapter(context, new ArrayList<>(productsCollection));
        productSpinnerAdapter.setDropDownViewResource(R.layout.spinner_list_item_layout);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_product_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductUsageRecord item = dataSet.get(position);
        holder.spinnerProducts.setAdapter(productSpinnerAdapter);
        int index = Stream.of(productsCollection).map(i -> i.getId()).collect(Collectors.toList()).indexOf(item.getProductId());
        if(index > -1)
            holder.spinnerProducts.setSelection(index);

        holder.spinnerProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onRecycleItemSpinnerProductSelected(productSpinnerAdapter.getItem(i).getId(), dataSet.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.etProductAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listener.onRecycleItemTextChanged(charSequence.toString(), dataSet.get(position));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.btnDelete.setOnClickListener(view -> {
            listener.onRecycleItemRemoved(dataSet.get(position));
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton btnDelete;
        public EditText etProductAmount;
        public Spinner spinnerProducts;

        public ViewHolder(View itemView) {
            super(itemView);
            this.btnDelete = itemView.findViewById(R.id.btnDelete);
            this.etProductAmount = itemView.findViewById(R.id.etProductAmount);
            this.spinnerProducts = itemView.findViewById(R.id.spinProductList);
        }
    }

    public void setDataSet(List<ProductUsageRecord> dataSet) {
        this.dataSet = dataSet;
    }

    public void setListener(RecyclerViewEventListener listener) {
        this.listener = listener;
    }
}
