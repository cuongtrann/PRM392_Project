package com.example.project_prm392.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.project_prm392.R;
import com.example.project_prm392.adapters.ProductPagerAdapter;
import com.example.project_prm392.databinding.ActivityProductDetailBinding;
import com.example.project_prm392.fragments.DescriptionFragment;
import com.example.project_prm392.models.MyCartModel;
import com.example.project_prm392.product.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductPagerAdapter productPagerAdapter;
    private ViewPager viewPager;

    ActivityProductDetailBinding binding;

    private FirebaseFirestore firestore;

    Product product = null;

    ImageView detailedImg;

    FirebaseAuth auth;

    int totalQuantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");

        binding.btnBackProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(obj instanceof Product){
            product = (Product) obj;
        }
        detailedImg = findViewById(R.id.detailed_img);
        DescriptionFragment descFragment = new DescriptionFragment();
        if(product != null){
            Glide.with(getApplicationContext()).load(product.getImage()).into(detailedImg);
            binding.detailedName.setText(product.getName());
            binding.detailedPrice.setText("$"+product.getUnitPrice() + "");
            binding.detailedDesc.setText(product.getDescription());
        }

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    binding.quantityText.setText(totalQuantity + "");
                }
            }
        });

        binding.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    binding.quantityText.setText(totalQuantity + "");
                }
            }
        });
        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product);
            }
        });

    }

    private void addToCart(Product product){
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        MyCartModel cart = new MyCartModel("",product.getName(), (int) product.getUnitPrice(),"M",totalQuantity, product.getImage());

        firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String uuid = documentReference.getId();
                        cart.setUuid(uuid);
                        firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                                .collection("User").document(uuid)
                                .set(cart)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProductDetailActivity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                });

    }



}