package com.boiko.taisa.salon.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boiko.taisa.salon.R;
import com.boiko.taisa.salon.domain.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private Picasso picasso;
    private List<Category> dataSet;

    public CategoryRecyclerViewAdapter(List<Category> dataSet) {
        this.dataSet = dataSet;
        picasso = Picasso.get();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoty_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(dataSet.get(position).getTitle());
        holder.description.setText(dataSet.get(position).getDescription());
        picasso.load(dataSet.get(position).getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.ivIcon);
            this.title = itemView.findViewById(R.id.tvTitle);
            this.description = itemView.findViewById(R.id.tvDescription);
        }
    }

    public void setDataSet(List<Category> dataSet) {
        this.dataSet = dataSet;
    }
}
