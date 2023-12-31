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
import com.example.project_prm392.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, address, password, cfPassword;
    Button btnSignUp, btnLogin;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        this.name = findViewById(R.id.et_name);
        this.email = findViewById(R.id.et_email);
        this.address = findViewById(R.id.et_address);
        this.password = findViewById(R.id.et_password);
        this.cfPassword = findViewById(R.id.et_confirmPassword);
        this.btnSignUp = findViewById(R.id.btn_signup);
        this.btnLogin = findViewById(R.id.btn_login);

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
        String inputAddress = address.getText().toString();
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

        if(TextUtils.isEmpty(inputAddress)){
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
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

        firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("Name", name.getText().toString());
                    userData.put("Email", task.getResult().getUser().getEmail());
                    userData.put("IsAdmin", false);
                    userData.put("Address", address.getText().toString());
                    User user = new User(FirebaseAuth.getInstance().getUid(), task.getResult().getUser().getEmail(), inputPassword, name.getText().toString(), "false");
                    databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(user);
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