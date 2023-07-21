package com.example.project_prm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project_prm392.R;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnViewCustomers = findViewById(R.id.buttonViewAllCustomers);
        Button btnAddProduct = findViewById(R.id.buttonAdminAddProduct);
    }
}