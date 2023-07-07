package com.example.project_prm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_prm392.R;

public class SignUpActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText cfPassword;
    Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

    private void OnClickBtnSignUp(){
        String inputName = name.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();
        String inputCfPassword = cfPassword.getText().toString();

        if(TextUtils.isEmpty(inputName)){
            Toast.makeText(this,"Enter name!", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(this,"Enter email!", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(inputPassword)){
            Toast.makeText(this,"Enter password!", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(inputCfPassword)){
            Toast.makeText(this,"Confirm your password!", Toast.LENGTH_SHORT).show();
        }

        if(!inputPassword.equals(inputCfPassword)){
            Toast.makeText(this,"Password did not matched!", Toast.LENGTH_SHORT).show();
        }
    }

    private void OnClickBtnLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}