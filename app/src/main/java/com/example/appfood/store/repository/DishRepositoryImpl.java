package com.example.appfood.store.repository;

import androidx.annotation.NonNull;

import com.example.appfood.store.model.Dish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Firebase implementation of DishRepository
 */
public class DishRepositoryImpl implements DishRepository {

    private static final String DISHES_NODE = "dishes";
    private final DatabaseReference databaseReference;
    private static DishRepositoryImpl instance;

    private DishRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(DISHES_NODE);
    }

    public static synchronized DishRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new DishRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getAllDishes(OnDataLoadedCallback<List<Dish>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Dish> dishes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dish dish = dataSnapshot.getValue(Dish.class);
                    if (dish != null) {
                        dish.setDishId(dataSnapshot.getKey());
                        dishes.add(dish);
                    }
                }
                callback.onSuccess(dishes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getDishById(String dishId, OnDataLoadedCallback<Dish> callback) {
        databaseReference.child(dishId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dish dish = snapshot.getValue(Dish.class);
                if (dish != null) {
                    dish.setDishId(snapshot.getKey());
                    callback.onSuccess(dish);
                } else {
                    callback.onFailure("Dish not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void createDish(Dish dish, OnOperationCallback callback) {
        String dishId = databaseReference.push().getKey();
        if (dishId != null) {
            dish.setDishId(dishId);
            databaseReference.child(dishId).setValue(dish)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("Failed to generate dish ID");
        }
    }

    @Override
    public void updateDish(Dish dish, OnOperationCallback callback) {
        if (dish.getDishId() == null || dish.getDishId().isEmpty()) {
            callback.onFailure("Dish ID is required for update");
            return;
        }

        databaseReference.child(dish.getDishId()).setValue(dish)
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deleteDish(String dishId, OnOperationCallback callback) {
        databaseReference.child(dishId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void getDishesByShop(String shopId, OnDataLoadedCallback<List<Dish>> callback) {
        Query query = databaseReference.orderByChild("restaurant").equalTo(shopId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Dish> dishes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dish dish = dataSnapshot.getValue(Dish.class);
                    if (dish != null) {
                        dish.setDishId(dataSnapshot.getKey());
                        dishes.add(dish);
                    }
                }
                callback.onSuccess(dishes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }
}

