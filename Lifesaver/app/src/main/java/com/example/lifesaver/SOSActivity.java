package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SOSActivity extends AppCompatActivity {


    ImageView police, ambulance, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_s);

        ambulance = findViewById(R.id.ambulance_button);
        police = findViewById(R.id.police_button);
        home = findViewById(R.id.home_button);

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SOSActivity.this, MainActivity.class));
            }
        });
    }
}