package com.example.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private LinearLayout menuManageMenu;
    private LinearLayout menuManageOrders;
    private LinearLayout menuManagePromotions;
    private LinearLayout menuManageRevenue;
    private LinearLayout menuCustomerSupport;

    private LinearLayout navHome;
    private LinearLayout navNotifications;
    private LinearLayout navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        initViews();

        // Set welcome message
        setWelcomeMessage();

        // Setup menu click listeners
        setupMenuClickListeners();

        // Setup bottom navigation
        setupBottomNavigation();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        menuManageMenu = findViewById(R.id.menuManageMenu);
        menuManageOrders = findViewById(R.id.menuManageOrders);
        menuManagePromotions = findViewById(R.id.menuManagePromotions);
        menuManageRevenue = findViewById(R.id.menuManageRevenue);
        menuCustomerSupport = findViewById(R.id.menuCustomerSupport);

        navHome = findViewById(R.id.navHome);
        navNotifications = findViewById(R.id.navNotifications);
        navProfile = findViewById(R.id.navProfile);
    }

    private void setWelcomeMessage() {
        // Get store name from intent or SharedPreferences
        // For now, using placeholder
        String storeName = "Cửa hàng của bạn";
        tvWelcome.setText(String.format("Chào mừng %s", storeName));
    }

    private void setupMenuClickListeners() {
        // Manage Menu
        menuManageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DishListActivity.class);
                startActivity(intent);
            }
        });

        // Manage Orders
        menuManageOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrdersManagerActivity.class);
                startActivity(intent);
            }
        });

        // Manage Promotions
        menuManagePromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PromotionListActivity.class);
                startActivity(intent);
            }
        });

        // Manage Revenue
        menuManageRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SalesReportActivity.class);
                startActivity(intent);
            }
        });

        // Customer Support
        menuCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReviewListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBottomNavigation() {
        // Home Navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on home screen
                Toast.makeText(HomeActivity.this, "Trang chủ", Toast.LENGTH_SHORT).show();
            }
        });

        // Notifications Navigation
        navNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Thông báo", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to Notifications Activity
            }
        });

        // Profile Navigation
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Tài khoản", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to Profile Activity
            }
        });
    }
}

