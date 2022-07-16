package com.example.mentalstatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView depressionTest, anxietyTest, stressTest, sleepdisorderTest, panicdisorderTest, eatingdisorderTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        depressionTest = findViewById(R.id.DepressionTest);
        anxietyTest = findViewById(R.id.AnxietyTest);
        stressTest = findViewById(R.id.StressTest);
        sleepdisorderTest = findViewById(R.id.SleepDisorderTest);
        panicdisorderTest = findViewById(R.id.PanicDisorderTest);
        eatingdisorderTest = findViewById(R.id.EatingDisorderTest);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.getMenu().findItem(R.id.menuLogout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);




        depressionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depTest();
            }
        });

        anxietyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anxTest();
            }
        });

        stressTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strTest();
            }
        });

        sleepdisorderTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepTest();
            }
        });

        panicdisorderTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panicTest();
            }
        });

        eatingdisorderTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eatingTest();
            }
        });


    }

    public void depTest(){
        startActivity(new Intent(getApplicationContext(),Landing_DepTest.class));
    }

    public void anxTest(){
        startActivity(new Intent(getApplicationContext(),Landing_AnxTest.class));
    }

    public void strTest(){
        startActivity(new Intent(getApplicationContext(), Landing_StrTest.class));
    }

    public void sleepTest(){
        startActivity(new Intent(getApplicationContext(), Landing_SleepDisorderTest.class));
    }

    public void panicTest(){
        startActivity(new Intent(getApplicationContext(), Landing_PanicDisorder.class));
    }

    public void eatingTest(){
        startActivity(new Intent(getApplicationContext(), Landing_EatingDisorderTest.class));
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}