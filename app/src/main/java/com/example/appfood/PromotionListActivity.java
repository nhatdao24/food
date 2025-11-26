package com.example.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                    try {
                        Promotion promotion = new Promotion();
                        promotion.setPromotionId(dataSnapshot.getKey());

                        // Safely get voucherName (new) or promotionName (old)
                        if (dataSnapshot.child("voucherName").exists()) {
                            promotion.setVoucherName(dataSnapshot.child("voucherName").getValue(String.class));
                        } else if (dataSnapshot.child("promotionName").exists()) {
                            promotion.setVoucherName(dataSnapshot.child("promotionName").getValue(String.class)); // Fallback to old field
                        }

                        // Safely get discountValue
                        if (dataSnapshot.child("discountValue").exists()) {
                            promotion.setDiscountValue(String.valueOf(dataSnapshot.child("discountValue").getValue()));
                        }

                        // Safely get status
                        if (dataSnapshot.child("status").exists()) {
                            Object statusObj = dataSnapshot.child("status").getValue();
                            if (statusObj instanceof Long) {
                                promotion.setStatus(((Long) statusObj).intValue());
                            } else if (statusObj instanceof String) {
                                promotion.setStatus(Integer.parseInt((String) statusObj));
                            }
                        }

                        promotionList.add(promotion);
                    } catch (Exception e) {
                        // Ignore corrupted/malformed records
                    }
                }
                promotionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PromotionListActivity.this, "Không thể tải danh sách khuyến mãi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
