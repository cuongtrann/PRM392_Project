package com.example.project_prm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_prm392.R;
import com.example.project_prm392.models.MyCartModel;
import com.example.project_prm392.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    int orderId;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        subTotal = findViewById(R.id.subTotal);
        total = findViewById(R.id.total);
        payment = findViewById(R.id.payment);
        address = findViewById(R.id.address);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String subTotalPrice = extra.getString("subTotalPrice");
            subTotal.setText("$" + subTotalPrice);
            String totalPrice = extra.getString("totalPrice");
            total.setText(totalPrice);
            List<MyCartModel> cartList = (List<MyCartModel>) extra.getSerializable("cart");
        }

        auth = FirebaseAuth.getInstance();
        String uId = auth.getUid();
        firestore = FirebaseFirestore.getInstance();

        //get Address form FireBase
        firestore.collection("UserData")
                .document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        String Address = (String)document.getData().get("Address");
                        address.setText(Address);
                    }
                });

        String id = firestore.collection("Order").
                document(auth.getUid()).collection("UserData").getId();

        firestore.collection("Order")
                .document(auth.getUid())
                .collection("UserData")
                .orderBy("orderId", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            if (snapshot != null && !snapshot.isEmpty()) {
                                DocumentSnapshot document = snapshot.getDocuments().get(0);
                                int lastOrderId = document.getLong("orderId").intValue();
                                orderId = lastOrderId + 1; // Update orderId here
                            } else {
                                orderId = 1; // Set orderId to 1 if there are no documents
                            }
                            Order order = AddOrder(orderId,id);
                            Map<String, Order> map = new HashMap<>();
                            map.put("Order", order);
                            //add order to Firebase
                            firestore.collection("Order").document(auth.getUid()).collection("UserData").add(map);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        orderId = 1;
                    }
                });


        //Click Button Payment go to OrderSuccessActivity
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
    }

    @NonNull
    private Order AddOrder(int orderId, String uId) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String time = currentDate.format(formatter);
        Order order = new Order(orderId, uId, time);
        return order;
    }

}