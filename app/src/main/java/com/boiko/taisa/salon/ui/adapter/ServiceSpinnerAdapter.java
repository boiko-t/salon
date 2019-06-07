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
import com.boiko.taisa.salon.domain.entity.SalonService;

import java.util.ArrayList;
import java.util.List;

public class ServiceSpinnerAdapter extends ArrayAdapter<SalonService> {
    private Context context;
    private List<SalonService> list;

    public ServiceSpinnerAdapter(@NonNull Context context, @LayoutRes ArrayList<SalonService> list) {
        super(context, 0 , list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.spinner_list_item_layout, parent, false);
        SalonService currentService = list.get(position);
        TextView title = listItem.findViewById(R.id.tvTitle);

        title.setText(currentService.getName());
        return listItem;
    }

    public void setList(List<SalonService> list) {
        this.list = list;
    }
}
