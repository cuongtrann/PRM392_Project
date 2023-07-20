package com.example.project_prm392.product;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_prm392.R;
import com.example.project_prm392.category.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowProductActivity extends AppCompatActivity {
    ArrayList<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    TextView txtProductType;
    ImageView btnSearch;

    TextView txtFilter;

    FirebaseFirestore db;

    ImageButton btnBackProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra("CATEGORY");
        Log.d("CATEGORY2", category.getName());

        txtProductType = findViewById(R.id.txtProductType);
        txtProductType.setText(category.getName());

        btnBackProduct= findViewById(R.id.btnBackProduct);
        btnBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        productList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                if (product.getCategory().equals(category.getName())){
                                    productList.add(product);
                                    productAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        productAdapter = new ProductAdapter(productList, ShowProductActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(productAdapter);

        recyclerView.setLayoutManager(layoutManager);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });

        txtFilter = findViewById(R.id.txtFilter);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

    }

    private void showSearchDialog() {
        Dialog searchDialog = new Dialog(this);
        searchDialog.setContentView(R.layout.product_search_dialog);

        EditText editTextSearch = searchDialog.findViewById(R.id.editTextSearch);
        Button buttonSearch = searchDialog.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editTextSearch.getText().toString().trim();
                // Gọi hàm để thực hiện tìm kiếm với keyword
                performSearch(keyword);

                searchDialog.dismiss(); // Đóng dialog sau khi tìm kiếm
            }
        });
//
//        Button buttonCancel = searchDialog.findViewById(R.id.buttonCancel);
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchDialog.dismiss(); // Đóng dialog nếu click nút "Cancel"
//            }
//        });

        searchDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void performSearch(String keyword) {
        List<Product> filteredProducts = new ArrayList<>();

        // Lặp qua danh sách sản phẩm ban đầu để tìm các sản phẩm có tên chứa keyword
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        // Cập nhật danh sách sản phẩm hiển thị trong RecyclerView
        productAdapter.setProducts(filteredProducts);
        productAdapter.notifyDataSetChanged();
    }

    private void showFilterDialog() {
        Dialog filterDialog = new Dialog(this);
        filterDialog.setContentView(R.layout.product_filter_dialog);

        EditText editTextMinPrice = filterDialog.findViewById(R.id.editTextMinPrice);
        EditText editTextMaxPrice = filterDialog.findViewById(R.id.editTextMaxPrice);

        Button buttonSearch = filterDialog.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double min = null;
                Double max = null;
                if (Strings.isNullOrEmpty(editTextMinPrice.getText().toString())){
                    min = 0d;
                }
                if (Strings.isNullOrEmpty(editTextMaxPrice.getText().toString())){
                    max = Double.MAX_VALUE;
                }
                min = min==null?(Double.parseDouble(editTextMinPrice.getText().toString().trim())):min;
                max = max==null?(Double.parseDouble(editTextMaxPrice.getText().toString().trim())):max;

                // Gọi hàm để thực hiện tìm kiếm theo giá (minPrice và maxPrice)
                performFilterByPrice(min, max);

                filterDialog.dismiss(); // Đóng dialog sau khi lọc
            }
        });

//        Button buttonCancel = filterDialog.findViewById(R.id.buttonCancel);
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterDialog.dismiss(); // Đóng dialog nếu click nút "Cancel"
//            }
//        });

        filterDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void performFilterByPrice(double minPrice, double maxPrice) {
        List<Product> filteredProducts = new ArrayList<>();
        // Lặp qua danh sách sản phẩm ban đầu để lọc các sản phẩm theo giá
        for (Product product : productList) {
            if (product.getUnitPrice() >= minPrice && product.getUnitPrice() <= maxPrice) {
                filteredProducts.add(product);
            }
        }
        // Cập nhật danh sách sản phẩm hiển thị trong RecyclerView
        productAdapter.setProducts(filteredProducts);
        productAdapter.notifyDataSetChanged();
    }
}