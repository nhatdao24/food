package com.example.appfood.store.repository;

import androidx.annotation.NonNull;

import com.example.appfood.store.model.Promotion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Firebase implementation of PromotionRepository
 */
public class PromotionRepositoryImpl implements PromotionRepository {

    private static final String PROMOTIONS_NODE = "promotions";
    private final DatabaseReference databaseReference;
    private static PromotionRepositoryImpl instance;

    private PromotionRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(PROMOTIONS_NODE);
    }

    public static synchronized PromotionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PromotionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getAllPromotions(OnDataLoadedCallback<List<Promotion>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Promotion> promotions = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Promotion promotion = dataSnapshot.getValue(Promotion.class);
                    if (promotion != null) {
                        promotion.setPromotionId(dataSnapshot.getKey());
                        promotions.add(promotion);
                    }
                }
                callback.onSuccess(promotions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getPromotionById(String promotionId, OnDataLoadedCallback<Promotion> callback) {
        databaseReference.child(promotionId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Promotion promotion = snapshot.getValue(Promotion.class);
                if (promotion != null) {
                    promotion.setPromotionId(snapshot.getKey());
                    callback.onSuccess(promotion);
                } else {
                    callback.onFailure("Promotion not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void createPromotion(Promotion promotion, OnOperationCallback callback) {
        String promotionId = databaseReference.push().getKey();
        if (promotionId != null) {
            promotion.setPromotionId(promotionId);
            databaseReference.child(promotionId).setValue(promotion)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("Failed to generate promotion ID");
        }
    }

    @Override
    public void updatePromotion(Promotion promotion, OnOperationCallback callback) {
        if (promotion.getPromotionId() == null || promotion.getPromotionId().isEmpty()) {
            callback.onFailure("Promotion ID is required for update");
            return;
        }

        databaseReference.child(promotion.getPromotionId()).setValue(promotion)
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deletePromotion(String promotionId, OnOperationCallback callback) {
        databaseReference.child(promotionId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void getActivePromotions(OnDataLoadedCallback<List<Promotion>> callback) {
        Query query = databaseReference.orderByChild("status").equalTo(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Promotion> promotions = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Promotion promotion = dataSnapshot.getValue(Promotion.class);
                    if (promotion != null) {
                        promotion.setPromotionId(dataSnapshot.getKey());
                        promotions.add(promotion);
                    }
                }
                callback.onSuccess(promotions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }
}

