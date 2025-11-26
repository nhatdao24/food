package com.example.appfood.store.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.store.controller.PromotionController;
import com.example.appfood.store.model.Promotion;
import com.example.appfood.store.repository.PromotionRepository;
import com.example.appfood.store.view.adapter.PromotionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * View class for displaying list of promotions
 * Uses PromotionController for business logic
 */
public class PromotionListActivity extends AppCompatActivity {

    private TextView btnBack;
    private Button btnAddPromotion;
    private LinearLayout navHome, navNotifications, navProfile;
    private RecyclerView rvPromotionList;
    private PromotionAdapter promotionAdapter;
    private ArrayList<Promotion> promotionList;

    private PromotionController promotionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_promotion_list);

        promotionController = PromotionController.getInstance();

        initViews();
        setupListeners();
        setupRecyclerView();
        fetchPromotions();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnAddPromotion = findViewById(R.id.btnAddPromotion);
        navHome = findViewById(R.id.navHome);
        navNotifications = findViewById(R.id.navNotifications);
        navProfile = findViewById(R.id.navProfile);
        rvPromotionList = findViewById(R.id.rvPromotionList);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnAddPromotion.setOnClickListener(v -> {
            Intent intent = new Intent(PromotionListActivity.this, AddPromotionActivity.class);
            startActivity(intent);
        });

        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(PromotionListActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        navNotifications.setOnClickListener(v -> {
            Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show();
        });

        navProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Tài khoản", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        rvPromotionList.setLayoutManager(new LinearLayoutManager(this));
        promotionList = new ArrayList<>();
        promotionAdapter = new PromotionAdapter(this, promotionList);
        rvPromotionList.setAdapter(promotionAdapter);
    }

    private void fetchPromotions() {
        promotionController.getAllPromotions(new PromotionRepository.OnDataLoadedCallback<List<Promotion>>() {
            @Override
            public void onSuccess(List<Promotion> promotions) {
                promotionList.clear();
                promotionList.addAll(promotions);
                promotionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(PromotionListActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPromotions();
    }
}
