package com.example.project_prm392.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_prm392.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CateViewHolder> {
    private List<Category> categories;
    private Context mContext;

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View categoryView = inflater.inflate(R.layout.category, parent,false);
        CateViewHolder cateViewHolder = new CateViewHolder(categoryView, categories);
        return cateViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CateViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.cateName.setText(category.getName());

        holder.productCount.setText(String.valueOf(category.getQuantity()));
        holder.imageView.setImageResource(category.getImg());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
