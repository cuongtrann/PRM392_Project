package com.example.project_prm392.product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Button buttonLoadCSV = findViewById(R.id.buttonLoadCSV);
        buttonLoadCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở trình duyệt tệp để cho phép người dùng chọn file CSV
                readData();
            }
        });

        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtName = findViewById(R.id.editTextName);
                EditText txtDescription = findViewById(R.id.editTextDescription);
                EditText txtImage = findViewById(R.id.editTextImage);
                EditText txtCategory = findViewById(R.id.editTextCategory);
                EditText txtPrice = findViewById(R.id.editTextUnitPrice);
                EditText txtUnitsInStock = findViewById(R.id.editTextUnitsInStock);

                Product product = new Product();
                product.setName(txtName.getText().toString());
                product.setCategory(txtCategory.getText().toString());
                product.setDescription(txtDescription.getText().toString());
                product.setImage(txtImage.getText().toString());
                product.setUnitPrice(Double.parseDouble(txtPrice.getText().toString()));
                product.setUnitsInStock(Integer.parseInt(txtUnitsInStock.getText().toString()));

                addProductToFirebase(product);
                Toast.makeText(AddProductActivity.this, "Đã thêm sản phẩm lên Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void readData(){
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            while ((line = reader.readLine()) != null){
                    //Split by ,
                String[] tokens = line.split(",");
                //Read data
                Product product = new Product();
                product.setName(tokens[0]);
                product.setCategory(tokens[1]);
                product.setDescription(tokens[2]);
                product.setImage(tokens[3]);
                product.setUnitPrice(Double.parseDouble(tokens[4]));
                product.setUnitsInStock(Integer.parseInt(tokens[5]));

                productList.add(product);
            }
            reader.close();
            for (Product product : productList) {
                addProductToFirebase(product);
            }
            Toast.makeText(this, "Đã thêm sản phẩm lên Firebase", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.d("ReadFile", "Error read file in line " + line, e);
        }
    }

    // Phương thức để thêm danh sách sản phẩm lên Firebase
    private void addProductsToFirebase() {


        // Hiển thị thông báo khi hoàn thành

    }

    private void addProductToFirebase(Product product) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Product");
        Log.d("Product", product.getName());
        // Thêm sản phẩm vào Firebase Realtime Database
        collectionReference.add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("ADD SUCCESS", "add success");
                        } else {
                            // Xử lý lỗi nếu có
                        }
                    }
                });
    }



}
