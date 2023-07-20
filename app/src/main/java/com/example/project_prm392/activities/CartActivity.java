package com.example.project_prm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.project_prm392.R;
import com.example.project_prm392.adapters.MyCartAdapter;
import com.example.project_prm392.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList = new ArrayList<>();
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    TextView subTotal;

    TextView quantityCart;

    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        subTotal = findViewById(R.id.text_subtotal);
        totalPrice = findViewById(R.id.text_total);
        quantityCart = findViewById(R.id.quantityCart);
        recyclerView = findViewById(R.id.recycle_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyCartModel myCartModel = new MyCartModel("Sofa", 20, "M", 10, "");
        cartModelList.add(myCartModel);
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);
//        firestore.collection("Cart").document(FirebaseAuth.getInstance().getUid())
//                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (DocumentSnapshot doc :task.getResult().getDocuments())
//                            {
//                                MyCartModel myCartModel = doc.toObject(MyCartModel.class);
//
//                            }
//                        }
//                    }
//                });

        subTotal.setText("$" + getTotalPrice());
        double total = getTotalPrice() + 5;
        totalPrice.setText("$" + total);
        int cartSize = cartModelList.size();
        String quantityText = cartSize == 0 ? "List cart is empty" : cartSize == 1 ? "1 item" : cartSize + " items";
        quantityCart.setText(quantityText);
    }

    public double getTotalPrice()
    {   double total = 0;
        for (MyCartModel c:cartModelList)
        {
            total += c.getPrice() * c.getQuantity();
        }
        return total;
    }


}