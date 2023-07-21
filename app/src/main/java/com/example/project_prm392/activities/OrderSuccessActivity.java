package com.example.project_prm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_prm392.R;
import com.example.project_prm392.category.ShowCategoryActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView message;

    Button continute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        message = findViewById(R.id.orderId);
        continute = findViewById(R.id.continute);

        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        message.setText("Your order #" + orderId + " is completed");

        continute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSuccessActivity.this , ShowCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}