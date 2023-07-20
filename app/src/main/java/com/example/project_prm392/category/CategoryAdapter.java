package com.example.project_prm392.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.project_prm392.R;
import com.google.firebase.firestore.FirebaseFirestore;

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
//        Category category = categories.get(position);
//        holder.cateName.setText(category.getName());
//
//        holder.productCount.setText(String.valueOf(category.getQuantity()));
//        holder.imageView.setImageResource(category.getImg());

        Glide.with(mContext).load(categories.get(position).getImage()).into(holder.imageView);
        holder.cateName.setText(categories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImg;
        TextView catName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.categoryImage);
            catName = itemView.findViewById(R.id.categoryName);
        }
    }
}
