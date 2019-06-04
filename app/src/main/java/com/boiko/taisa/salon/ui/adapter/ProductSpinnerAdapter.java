package com.boiko.taisa.salon.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boiko.taisa.salon.R;
import com.boiko.taisa.salon.domain.entity.Product;
import com.boiko.taisa.salon.domain.entity.SalonService;

import java.util.ArrayList;
import java.util.List;

public class ProductSpinnerAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private List<Product> list;

    public ProductSpinnerAdapter(@NonNull Context context, @LayoutRes ArrayList<Product> list) {
        super(context, 0 , list);
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        Product currentProduct = list.get(position);
        TextView title = listItem.findViewById(R.id.tvTitle);

        title.setText(currentProduct.getName());
        return listItem;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
