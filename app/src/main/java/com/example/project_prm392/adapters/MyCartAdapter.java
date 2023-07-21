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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.project_prm392.R;
import com.example.project_prm392.models.MyCartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context context;
    private List<MyCartModel> list;

    int totalAmount = 0;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public MyCartAdapter(Context context, List<MyCartModel> list, FirebaseAuth auth,  FirebaseFirestore firestore) {
        this.context = context;
        this.list = list;
        this.firestore = firestore;
        this.auth = auth;
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
        RequestOptions options = new RequestOptions().transform(new RoundedCorners(20));
        Glide.with(context).load(list.get(position).getImage()).apply(options).into(holder.imageProduct);
        holder.deleteCart.setOnClickListener(v -> {
            deleteItemFromFirestore(list.get(position));
            list.remove(position);
            notifyDataSetChanged();
            updateTotalAmountAndBroadcast();
            // Notify the adapter that the item was removed, this will refresh the RecyclerView
        });
        totalAmount = totalAmount + list.get(position).getPrice() *  list.get(position).getQuantity();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    private void deleteItemFromFirestore(MyCartModel itemToDelete) {
        firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                .collection("User").document(itemToDelete.getUuid())
                .delete();
    }

    private void updateTotalAmountAndBroadcast() {
        totalAmount = 0;
        for (MyCartModel item : list) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

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
            imageProduct = itemView.findViewById(R.id.product_image);
        }
    }
}
