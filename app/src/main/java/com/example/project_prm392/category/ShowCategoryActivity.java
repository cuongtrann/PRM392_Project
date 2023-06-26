package com.example.project_prm392.category;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.R;

import java.util.ArrayList;

public class ShowCategoryActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category);

        recyclerView = findViewById(R.id.cateRecycleView);
        categories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            categories.add(new Category("Table Wood Pine",10, R.drawable.chair1));
        }

        categoryAdapter = new CategoryAdapter(categories, ShowCategoryActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(categoryAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}