package com.example.project_prm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project_prm392.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SetBtnLoginOnClickEvent();
        SetBtnSignUpOnClickEvent();
    }

    private void SetBtnLoginOnClickEvent(){
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(v -> OnClickBtnLogin());
    }

    private void OnClickBtnLogin(){
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

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