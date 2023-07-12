package com.example.project_prm392.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, cfPassword;
    Button btnSignUp, btnLogin;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        cfPassword = findViewById(R.id.et_confirmPassword);
        btnSignUp = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnSignUp.setOnClickListener(view -> {
            OnClickBtnSignUp();
        });
        btnLogin.setOnClickListener(view -> {
            OnClickBtnLogin();
        });
    }

    private void OnClickBtnSignUp() {
        String inputName = name.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();
        String inputCfPassword = cfPassword.getText().toString();

        if (TextUtils.isEmpty(inputName)) {
            Toast.makeText(this, "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputEmail)) {
            Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputPassword)) {
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputCfPassword)) {
            Toast.makeText(this, "Confirm your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!inputPassword.equals(inputCfPassword)) {
            Toast.makeText(this, "Password did not matched!", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: check whether email has existed


        firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("Name", name.getText().toString());
                    userData.put("Email",task.getResult().getUser().getEmail());
                    userData.put("IsAdmin",false);
                    firestore.collection("UserData").document(task.getResult().getUser().getUid())
                            .set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    OnClickBtnLogin();
                                }
                            });
                }
            }
        });
    }

    private void OnClickBtnLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}