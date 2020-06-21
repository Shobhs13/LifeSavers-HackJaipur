package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.FileReader;
public class SplashActivity extends AppCompatActivity {

    boolean loginFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            loginFlag = true;

            //get data things

            //if
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!loginFlag)
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 2000);
    }
}