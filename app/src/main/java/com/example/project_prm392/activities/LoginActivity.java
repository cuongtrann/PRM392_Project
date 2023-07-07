package com.example.project_prm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

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

        firebaseAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //change to main activity
                }

                if(task.isCanceled()){
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