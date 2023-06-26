package com.example.project_prm392.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_prm392.R;

import java.util.List;

public class CateViewHolder extends RecyclerView.ViewHolder{
    List<Category> categoryList;
    public TextView cateName;
    public TextView productCount;
    public ImageView imageView;

    public CateViewHolder(@NonNull View itemView, List<Category> categoryList) {
        super(itemView);
        this.categoryList = categoryList;
        imageView = itemView.findViewById(R.id.categoryImage);

        cateName = itemView.findViewById(R.id.categoryName);
        productCount = itemView.findViewById(R.id.productCount);

    }
}
