package com.example.project_prm392.category;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_prm392.R;
import com.example.project_prm392.product.ShowProductActivity;

import java.util.List;

public class CateViewHolder extends RecyclerView.ViewHolder{
    List<Category> categoryList;
    public TextView cateName;
    public TextView productCount;
    public ImageView imageView;
    public ImageView btnDetail;

    public CateViewHolder(@NonNull View itemView, List<Category> categoryList) {
        super(itemView);
        this.categoryList = categoryList;
        imageView = itemView.findViewById(R.id.categoryImage);

        cateName = itemView.findViewById(R.id.categoryName);
        productCount = itemView.findViewById(R.id.productCount);

        btnDetail = itemView.findViewById(R.id.btnCateDetail);

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Category category = categoryList.get(position);
                    Log.d("CATEGORY", category.getName());
                    Intent intent = new Intent(v.getContext(), ShowProductActivity.class);
                    intent.putExtra("CATEGORY", category);
                    v.getContext().startActivity(intent);
                }
                Toast.makeText(itemView.getContext(), categoryList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
