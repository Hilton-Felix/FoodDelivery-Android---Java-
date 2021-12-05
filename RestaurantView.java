package com.project.us4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class RestaurantView extends AppCompatActivity {
    LinearLayout homeLnl,settingsLnl, profileLnl;
    EditText searchRestaurant;
    ScrollView app_bar;
    private LinearLayoutManager restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        initViews();

        searchRestaurant = findViewById(R.id.searchRestaurant);
        app_bar = findViewById(R.id.app_bar);



        profileLnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RestaurantView.this, UserProfile.class);
                startActivity(intent);
            }
        });
        settingsLnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(RestaurantView.this, Settings.class);
                //startActivity(intent);
            }
        });
        homeLnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RestaurantView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        homeLnl=findViewById(R.id.home1_layout);
        settingsLnl=findViewById(R.id.settings1_layout);
        profileLnl=findViewById(R.id.profile1_layout);
    }
}