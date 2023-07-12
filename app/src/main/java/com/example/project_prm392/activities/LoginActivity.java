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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.et_loginEmail);
        password = findViewById(R.id.et_loginPassword);

        SetBtnLoginOnClickEvent();
        SetBtnSignUpOnClickEvent();
    }

    private void SetBtnLoginOnClickEvent(){
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(v -> OnClickBtnLogin());
    }

    private void OnClickBtnLogin(){
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firestore.collection("UserData")
                            .document(task.getResult().getUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    boolean isAdmin = (boolean)document.getData().get("IsAdmin");
                                    if(isAdmin){
                                        Toast.makeText(LoginActivity.this, "Login as admin", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Login as user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                else {
                    Toast.makeText(LoginActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SetBtnSignUpOnClickEvent(){
        Button btnSignUp = (Button) findViewById(R.id.buttonSignup);
        btnSignUp.setOnClickListener(v -> OnClickBtnSignUp());
    }

    private void OnClickBtnSignUp(){
        ChangeActivity(SignUpActivity.class);
    }

    private void ChangeActivity(Class target){
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}