package com.example.project_prm392.category;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.R;
import com.example.project_prm392.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowCategoryActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    ImageButton btnBack, btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.cateRecycleView);
        btnBack = findViewById(R.id.btnBackCate);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> this.OnClickBtnLogout());
        categories = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categories.add(category);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        categoryAdapter = new CategoryAdapter(categories, ShowCategoryActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(categoryAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }

    private void OnClickBtnLogout(){
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
    }
}