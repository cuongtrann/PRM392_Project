package com.example.project_prm392.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project_prm392.R;
import com.example.project_prm392.category.Category;
import com.example.project_prm392.models.MyCartModel;
import com.example.project_prm392.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    TextView subTotal;

    TextView total;

    Button payment;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    TextView address, shippingFee;

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        subTotal = findViewById(R.id.subTotal);
        total = findViewById(R.id.total);
        payment = findViewById(R.id.payment);
        address = findViewById(R.id.address);
        shippingFee = findViewById(R.id.shippingFee);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String subTotalPrice = extra.getString("subTotalPrice");
            subTotal.setText("$" + subTotalPrice);
            String totalPrice = extra.getString("totalPrice");
            total.setText(totalPrice);
            String ship = extra.getString("shipPrice");
            shippingFee.setText(ship);
            List<MyCartModel> cartList = (List<MyCartModel>) extra.getSerializable("cart");
        }

        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        String uId = auth.getUid();
        firestore = FirebaseFirestore.getInstance();

        //get Address form FireBase
        firestore.collection("UserData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getId().equals(uId)) {
                            String Address = (String) document.getData().get("Address");
                            address.setText(Address);
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        //Click Button Payment go to OrderSuccessActivity
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = AddOrder(uId);
                Map<String, Object> data = new HashMap<>();
                data.put("userId", order.getUserId());
                data.put("orderDate", order.getOrderDate());
                // Add other order fields to the map

                firestore.collection("Order").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String orderId = documentReference.getId();
                        Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                    }
                });

                //Get cartId to Delete
                firestore.collection("Cart").document(auth.getCurrentUser().getUid()).
                        collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        firestore.collection("Cart").document(auth.getCurrentUser().getUid()).
                                                collection("User").document(doc.getId()).delete();
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });


            }
        });
    }

    @NonNull
    private Order AddOrder( String uId) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String time = currentDate.format(formatter);
        Order order = new Order(uId, time);
        return order;
    }

}