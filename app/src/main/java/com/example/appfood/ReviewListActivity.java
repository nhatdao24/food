package com.example.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewListActivity extends AppCompatActivity {
    private TextView btnBack;
    private LinearLayout navHome, navNotifications, navProfile;

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

        // Review 1 buttons
        Button btnDelete1 = findViewById(R.id.btnDelete1);
        Button btnReply1 = findViewById(R.id.btnReply1);

        btnDelete1.setOnClickListener(v -> {
            Toast.makeText(this, "Đã xóa đánh giá", Toast.LENGTH_SHORT).show();
        });

        btnReply1.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewListActivity.this, ReviewDetailActivity.class);
            startActivity(intent);
        });

        // Review 2 buttons
        Button btnDelete2 = findViewById(R.id.btnDelete2);
        Button btnReply2 = findViewById(R.id.btnReply2);

        btnDelete2.setOnClickListener(v -> {
            Toast.makeText(this, "Đã xóa đánh giá", Toast.LENGTH_SHORT).show();
        });

        btnReply2.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewListActivity.this, ReviewDetailActivity.class);
            startActivity(intent);
        });

        // Bottom navigation
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

