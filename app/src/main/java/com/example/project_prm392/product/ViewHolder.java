package com.example.project_prm392.product;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.R;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {
    List<Product> productRecycleList;
    public TextView productName;
    public TextView price;
    public ImageView imageView;

    public ViewHolder(@NonNull View itemView, List<Product> productRecycleList) {
        super(itemView);
        this.productRecycleList = productRecycleList;
        imageView = itemView.findViewById(R.id.imgProduct);

        productName = itemView.findViewById(R.id.tvProductName);
        price = itemView.findViewById(R.id.tvProductPrice);;

    }
}
