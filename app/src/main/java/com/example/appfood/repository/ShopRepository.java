package com.example.appfood.repository;

import com.example.appfood.model.Shop;

import java.util.List;

/**
 * Repository interface for Shop data operations
 * This defines all CRUD operations for Shop entity
 */
public interface ShopRepository {

    /**
     * Get all shops from the database
     * @param callback Callback to handle the result
     */
    void getAllShops(OnDataLoadedCallback<List<Shop>> callback);

    /**
     * Get a specific shop by ID
     * @param shopId The shop ID
     * @param callback Callback to handle the result
     */
    void getShopById(String shopId, OnDataLoadedCallback<Shop> callback);

    /**
     * Create a new shop
     * @param shop The shop object to create
     * @param callback Callback to handle success/failure
     */
    void createShop(Shop shop, OnOperationCallback callback);

    /**
     * Update an existing shop
     * @param shop The shop object with updated data
     * @param callback Callback to handle success/failure
     */
    void updateShop(Shop shop, OnOperationCallback callback);

    /**
     * Delete a shop by ID
     * @param shopId The shop ID to delete
     * @param callback Callback to handle success/failure
     */
    void deleteShop(String shopId, OnOperationCallback callback);

    /**
     * Check if shop name already exists
     * @param shopName The shop name to check
     * @param callback Callback to handle the result
     */
    void isShopNameExists(String shopName, OnDataLoadedCallback<Boolean> callback);

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

