package com.example.project_prm392.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.R;
import com.example.project_prm392.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context context;
    private List<MyCartModel> list;

    int totalAmount = 0;



    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText("$" + list.get(position).getPrice());
        holder.quantity.setText(String.valueOf(list.get(position).getQuantity()));
        holder.size.setText(list.get(position).getSize());
        holder.deleteCart.setOnClickListener(v -> {
            list.remove(position);
            notifyDataSetChanged();
            Intent intent = new Intent("MyTotalAmount");
            intent.putExtra("totalAmount", totalAmount);
            // Notify the adapter that the item was removed, this will refresh the RecyclerView
        });
        totalAmount = totalAmount + list.get(position).getPrice() *  list.get(position).getQuantity();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView productName;
        TextView price;
        TextView quantity;
        ImageButton deleteCart;

        TextView size;


        TextView totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.quantity_cart);
            deleteCart = itemView.findViewById(R.id.delete_cart);
            size = itemView.findViewById(R.id.cart_size);
        }
    }
}
