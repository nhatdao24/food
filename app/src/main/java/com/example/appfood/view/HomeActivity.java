package com.example.appfood.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Home screen
 * Main dashboard for shop owner
 */
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

        initViews();
        setWelcomeMessage();
        setupMenuClickListeners();
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
        // Get store name from SharedPreferences or Intent
        String storeName = "Cửa hàng của bạn";
        tvWelcome.setText(String.format("Chào mừng %s", storeName));
    }

    private void setupMenuClickListeners() {
        menuManageMenu.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DishListActivity.class);
            startActivity(intent);
        });

        menuManageOrders.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, OrdersManagerActivity.class);
            startActivity(intent);
        });

        menuManagePromotions.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PromotionListActivity.class);
            startActivity(intent);
        });

        menuManageRevenue.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SalesReportActivity.class);
            startActivity(intent);
        });

        menuCustomerSupport.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ReviewListActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        navHome.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Trang chủ", Toast.LENGTH_SHORT).show();
        });

        navNotifications.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Thông báo", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to Notifications Activity
        });

        navProfile.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Tài khoản", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to Profile Activity
        });
    }
}

