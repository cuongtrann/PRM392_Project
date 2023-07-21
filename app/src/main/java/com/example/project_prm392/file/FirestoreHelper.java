package com.example.project_prm392.file;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm392.product.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreHelper {

    private static final String COLLECTION_NAME = "Product";

    public static void addProductToFirestore(Product product) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);

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
