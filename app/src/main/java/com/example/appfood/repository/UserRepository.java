package com.example.appfood.repository;

import com.example.appfood.model.User;

import java.util.List;

/**
 * Repository interface for User data operations
 * This defines all CRUD operations for User entity
 * Will be fully implemented when user functionality is developed
 */
public interface UserRepository {

    /**
     * Get all users from the database
     * @param callback Callback to handle the result
     */
    void getAllUsers(OnDataLoadedCallback<List<User>> callback);

    /**
     * Get a specific user by ID
     * @param userId The user ID
     * @param callback Callback to handle the result
     */
    void getUserById(String userId, OnDataLoadedCallback<User> callback);

    /**
     * Get user by email
     * @param email The user email
     * @param callback Callback to handle the result
     */
    void getUserByEmail(String email, OnDataLoadedCallback<User> callback);

    /**
     * Create a new user
     * @param user The user object to create
     * @param callback Callback to handle success/failure
     */
    void createUser(User user, OnOperationCallback callback);

    /**
     * Update an existing user
     * @param user The user object with updated data
     * @param callback Callback to handle success/failure
     */
    void updateUser(User user, OnOperationCallback callback);

    /**
     * Delete a user by ID
     * @param userId The user ID to delete
     * @param callback Callback to handle success/failure
     */
    void deleteUser(String userId, OnOperationCallback callback);

    /**
     * Check if username already exists
     * @param username The username to check
     * @param callback Callback to handle the result
     */
    void isUsernameExists(String username, OnDataLoadedCallback<Boolean> callback);

    /**
     * Check if email already exists
     * @param email The email to check
     * @param callback Callback to handle the result
     */
    void isEmailExists(String email, OnDataLoadedCallback<Boolean> callback);

    /**
     * Callback interface for data loaded operations
     */
    interface OnDataLoadedCallback<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }

    /**
     * Callback interface for CRUD operations
     */
    interface OnOperationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}

