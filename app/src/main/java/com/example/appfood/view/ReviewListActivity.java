package com.example.appfood.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Review List
 * TODO: Implement with ReviewController when ready
 */
public class ReviewListActivity extends AppCompatActivity {

    private TextView btnBack;
    private LinearLayout navHome;
    private LinearLayout navNotifications;
    private LinearLayout navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        navHome = findViewById(R.id.navHome);
        navNotifications = findViewById(R.id.navNotifications);
        navProfile = findViewById(R.id.navProfile);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewListActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        navNotifications.setOnClickListener(v -> {
            // TODO: Navigate to Notifications
        });

        navProfile.setOnClickListener(v -> {
            // TODO: Navigate to Profile
        });
    }
}

