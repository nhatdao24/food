package com.example.appfood.store.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.store.model.Notification;
import com.example.appfood.store.view.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationActivity - Hiển thị danh sách thông báo
 */
public class NotificationActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvNotifications;
    private LinearLayout layoutEmptyState;
    private ProgressBar progressBar;

    private NotificationAdapter adapter;
    private List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_notification);

        initializeViews();
        setupRecyclerView();
        setupListeners();
        loadNotifications();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        rvNotifications = findViewById(R.id.rvNotifications);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(this);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);

        adapter.setOnNotificationClickListener((notification, position) -> {
            handleNotificationClick(notification, position);
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadNotifications() {
        // Show loading
        showLoading(true);

        // TODO: Load from Firebase
        notifications = new ArrayList<>();

        // Simulate loading delay
        rvNotifications.postDelayed(() -> {
            showLoading(false);
            displayNotifications();
        }, 500);
    }


    private void displayNotifications() {
        if (notifications == null || notifications.isEmpty()) {
            showEmptyState(true);
        } else {
            showEmptyState(false);
            adapter.setNotifications(notifications);
        }
    }

    private void handleNotificationClick(Notification notification, int position) {
        // Mark as read
        notification.setRead(true);
        adapter.notifyItemChanged(position);

        // Handle based on type
        switch (notification.getType()) {
            case "order":
                // Navigate to order detail
                Toast.makeText(this, "Mở chi tiết đơn hàng", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to OrderDetailActivity
                break;

            case "promotion":
                // Navigate to promotion detail
                Toast.makeText(this, "Mở khuyến mãi", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to PromotionDetailActivity
                break;

            default:
                Toast.makeText(this, notification.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        rvNotifications.setVisibility(show ? View.GONE : View.VISIBLE);
        layoutEmptyState.setVisibility(View.GONE);
    }

    private void showEmptyState(boolean show) {
        layoutEmptyState.setVisibility(show ? View.VISIBLE : View.GONE);
        rvNotifications.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}

