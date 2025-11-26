package com.example.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PromotionListActivity extends AppCompatActivity {
    private TextView btnBack;
    private Button btnAddPromotion;
    private LinearLayout navHome, navNotifications, navProfile;
    private RecyclerView rvPromotionList;
    private PromotionAdapter promotionAdapter;
    private ArrayList<Promotion> promotionList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);
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
            // TODO: Navigate to Notifications
        });

        navProfile.setOnClickListener(v -> {
            // TODO: Navigate to Profile
        });
    }

    private void setupRecyclerView() {
        rvPromotionList.setLayoutManager(new LinearLayoutManager(this));
        promotionList = new ArrayList<>();
        promotionAdapter = new PromotionAdapter(this, promotionList);
        rvPromotionList.setAdapter(promotionAdapter);
    }

    private void fetchPromotions() {
        mDatabase = FirebaseDatabase.getInstance().getReference("promotions");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                promotionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Promotion promotion = dataSnapshot.getValue(Promotion.class);
                    if (promotion != null) {
                        promotion.setPromotionId(dataSnapshot.getKey());
                        promotionList.add(promotion);
                    }
                }
                promotionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
