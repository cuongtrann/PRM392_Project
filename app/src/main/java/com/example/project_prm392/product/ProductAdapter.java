package com.example.project_prm392.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm392.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Product> mProducts;
    private Context mContext;

    public ProductAdapter(List<Product> mProducts, Context context) {
        this.mProducts = mProducts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.product, parent,false);
        ViewHolder viewHolder = new ViewHolder(productView, mProducts);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(mProducts.get(position).getImage()).into(holder.imageView);
        holder.productName.setText(mProducts.get(position).getName());
        holder.price.setText("$" + String.valueOf(mProducts.get(position).getUnitPrice()));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
    }
}
