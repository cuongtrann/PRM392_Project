package com.example.project_prm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_prm392.R;
import com.example.project_prm392.adapters.MyCartAdapter;
import com.example.project_prm392.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    TextView subTotal;

    TextView quantityCart;

    TextView totalPrice;

    Button checkOut;

    List<MyCartModel> cart;
    TextView shipPriceText;

    int totalPriceValue, totalBill;
    int shipPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        shipPriceText = findViewById(R.id.text_shipping_fee);
        subTotal = findViewById(R.id.text_subtotal);
        totalPrice = findViewById(R.id.text_total);
        quantityCart = findViewById(R.id.quantityCart);
        recyclerView = findViewById(R.id.recycle_view_cart);
        checkOut = findViewById(R.id.checkout_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList, auth, firestore);
        recyclerView.setAdapter(cartAdapter);
        firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                                cartModelList.add(myCartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cart", (Serializable) cartModelList);
                intent.putExtra("subTotalPrice", String.valueOf(totalBill));
                intent.putExtra("totalPrice", String.valueOf(totalPriceValue));
                intent.putExtra("shipPrice", String.valueOf(shipPrice));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalBill = intent.getIntExtra("totalAmount", 0);
            subTotal.setText("$" + totalBill);
            shipPrice = (totalBill == 0) ? 0 : (totalBill >= 100) ? 10 : 5;
            shipPriceText.setText("$" + shipPrice);
            totalPriceValue = totalBill + shipPrice;
            totalPrice.setText("$" + totalPriceValue);
            int cartSize = cartModelList.size();
            String quantityText = cartSize == 0 ? "List cart is empty" : cartSize == 1 ? "1 item" : cartSize + " items";
            quantityCart.setText(quantityText);
        }
    };


}