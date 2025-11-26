package com.example.appfood.repository;

import androidx.annotation.NonNull;

import com.example.appfood.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Firebase implementation of UserRepository
 * Handles all User-related database operations using Firebase Realtime Database
 * This is a placeholder implementation for future development
 */
public class UserRepositoryImpl implements UserRepository {

    private static final String USERS_NODE = "users";
    private final DatabaseReference databaseReference;
    private static UserRepositoryImpl instance;

    /**
     * Private constructor for singleton pattern
     */
    private UserRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS_NODE);
    }

    /**
     * Get singleton instance
     */
    public static synchronized UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getAllUsers(OnDataLoadedCallback<List<User>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setUserId(dataSnapshot.getKey());
                        users.add(user);
                    }
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getUserById(String userId, OnDataLoadedCallback<User> callback) {
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    user.setUserId(snapshot.getKey());
                    callback.onSuccess(user);
                } else {
                    callback.onFailure("User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getUserByEmail(String email, OnDataLoadedCallback<User> callback) {
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            user.setUserId(dataSnapshot.getKey());
                            callback.onSuccess(user);
                            return;
                        }
                    }
                }
                callback.onFailure("User not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void createUser(User user, OnOperationCallback callback) {
        String userId = databaseReference.push().getKey();
        if (userId != null) {
            user.setUserId(userId);
            user.setCreatedAt(System.currentTimeMillis());
            user.setUpdatedAt(System.currentTimeMillis());

            databaseReference.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("Failed to generate user ID");
        }
    }

    @Override
    public void updateUser(User user, OnOperationCallback callback) {
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            callback.onFailure("User ID is required for update");
            return;
        }

        user.setUpdatedAt(System.currentTimeMillis());
        databaseReference.child(user.getUserId()).setValue(user)
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deleteUser(String userId, OnOperationCallback callback) {
        databaseReference.child(userId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void isUsernameExists(String username, OnDataLoadedCallback<Boolean> callback) {
        Query query = databaseReference.orderByChild("username").equalTo(username);
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

    @Override
    public void isEmailExists(String email, OnDataLoadedCallback<Boolean> callback) {
        Query query = databaseReference.orderByChild("email").equalTo(email);
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

