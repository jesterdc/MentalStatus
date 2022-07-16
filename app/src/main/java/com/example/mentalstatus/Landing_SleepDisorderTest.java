package com.example.mentalstatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Landing_SleepDisorderTest extends AppCompatActivity {

    AppCompatButton startButton, historyButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_sleep_disorder_test);

        startButton= findViewById(R.id.startBtn);
        historyButton = findViewById(R.id.historyBtn);
        backButton = findViewById(R.id.backBtn);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Landing_SleepDisorderTest.this, Test_SleepDisorder.class));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Landing_SleepDisorderTest.this, Result_History_SleepDisorder.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}