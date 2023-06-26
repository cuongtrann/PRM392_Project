package com.example.project_prm392.product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_prm392.R;

import java.util.ArrayList;

public class ShowProductActivity extends AppCompatActivity {
    ArrayList<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        recyclerView = findViewById(R.id.recyclerView);
        productList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            productList.add(new Product(R.drawable.p1, "Table Wood Pine", 10.99, 5, 15.99, "This is the description of Product 1"));
        }

        productAdapter = new ProductAdapter(productList, ShowProductActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(productAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}