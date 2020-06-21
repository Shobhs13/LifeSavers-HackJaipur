package com.example.lifesaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button button;
    EditText email, password, confirmPassword;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.login_button);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        confirmPassword = findViewById(R.id.login_confirm_paspassword);
        register = findViewById(R.id.login_register);

        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            });

        });


        register.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });


    }
}