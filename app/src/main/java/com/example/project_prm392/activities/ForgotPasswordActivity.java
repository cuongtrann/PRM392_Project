package com.example.project_prm392.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.et_loginEmail);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(v -> OnClickBtnResetPassword());
    }

    private void OnClickBtnResetPassword() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                    .addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           finish();
                       }
                       else{
                           Toast.makeText(this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                       }
                    });
        }
    }
}