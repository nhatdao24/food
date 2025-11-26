package com.example.appfood.repository;

import androidx.annotation.NonNull;

import com.example.appfood.model.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Firebase implementation of ShopRepository
 * Handles all Shop-related database operations using Firebase Realtime Database
 */
public class ShopRepositoryImpl implements ShopRepository {

    private static final String SHOPS_NODE = "shops";
    private final DatabaseReference databaseReference;
    private static ShopRepositoryImpl instance;

    /**
     * Private constructor for singleton pattern
     */
    private ShopRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(SHOPS_NODE);
    }

    /**
     * Get singleton instance
     */
    public static synchronized ShopRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ShopRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getAllShops(OnDataLoadedCallback<List<Shop>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Shop> shops = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Shop shop = dataSnapshot.getValue(Shop.class);
                    if (shop != null) {
                        shop.setStoreId(dataSnapshot.getKey());
                        shops.add(shop);
                    }
                }
                callback.onSuccess(shops);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getShopById(String shopId, OnDataLoadedCallback<Shop> callback) {
        databaseReference.child(shopId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shop shop = snapshot.getValue(Shop.class);
                if (shop != null) {
                    shop.setStoreId(snapshot.getKey());
                    callback.onSuccess(shop);
                } else {
                    callback.onFailure("Shop not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void createShop(Shop shop, OnOperationCallback callback) {
        String shopId = databaseReference.push().getKey();
        if (shopId != null) {
            shop.setStoreId(shopId);
            shop.setCreatedAt(System.currentTimeMillis());
            shop.setUpdatedAt(System.currentTimeMillis());

            databaseReference.child(shopId).setValue(shop)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("Failed to generate shop ID");
        }
    }

    @Override
    public void updateShop(Shop shop, OnOperationCallback callback) {
        if (shop.getStoreId() == null || shop.getStoreId().isEmpty()) {
            callback.onFailure("Shop ID is required for update");
            return;
        }

        shop.setUpdatedAt(System.currentTimeMillis());
        databaseReference.child(shop.getStoreId()).setValue(shop)
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deleteShop(String shopId, OnOperationCallback callback) {
        databaseReference.child(shopId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void isShopNameExists(String shopName, OnDataLoadedCallback<Boolean> callback) {
        Query query = databaseReference.orderByChild("storeName").equalTo(shopName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onSuccess(snapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }
}

