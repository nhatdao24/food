package com.example.appfood.controller;

import com.example.appfood.model.Shop;
import com.example.appfood.repository.ShopRepository;
import com.example.appfood.repository.ShopRepositoryImpl;

import java.util.List;

/**
 * Controller class for Shop operations
 * Acts as a bridge between View (Activity/Fragment) and Model (Repository)
 * Handles business logic for Shop-related operations
 */
public class ShopController {

    private final ShopRepository shopRepository;
    private static ShopController instance;

    /**
     * Private constructor for singleton pattern
     */
    private ShopController() {
        this.shopRepository = ShopRepositoryImpl.getInstance();
    }

    /**
     * Get singleton instance
     */
    public static synchronized ShopController getInstance() {
        if (instance == null) {
            instance = new ShopController();
        }
        return instance;
    }

    /**
     * Get all shops
     * @param callback Callback to handle the result
     */
    public void getAllShops(ShopRepository.OnDataLoadedCallback<List<Shop>> callback) {
        shopRepository.getAllShops(callback);
    }

    /**
     * Get shop by ID
     * @param shopId The shop ID
     * @param callback Callback to handle the result
     */
    public void getShopById(String shopId, ShopRepository.OnDataLoadedCallback<Shop> callback) {
        if (shopId == null || shopId.trim().isEmpty()) {
            callback.onFailure("Shop ID cannot be empty");
            return;
        }
        shopRepository.getShopById(shopId, callback);
    }

    /**
     * Create a new shop with validation
     * @param shop The shop to create
     * @param callback Callback to handle success/failure
     */
    public void createShop(Shop shop, ShopRepository.OnOperationCallback callback) {
        // Validate shop data
        String validationError = validateShop(shop);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        // Check if shop name already exists
        shopRepository.isShopNameExists(shop.getStoreName(), new ShopRepository.OnDataLoadedCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean exists) {
                if (exists) {
                    callback.onFailure("Tên cửa hàng đã tồn tại");
                } else {
                    shopRepository.createShop(shop, callback);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    /**
     * Update an existing shop
     * @param shop The shop with updated data
     * @param callback Callback to handle success/failure
     */
    public void updateShop(Shop shop, ShopRepository.OnOperationCallback callback) {
        // Validate shop data
        String validationError = validateShop(shop);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        if (shop.getStoreId() == null || shop.getStoreId().trim().isEmpty()) {
            callback.onFailure("Shop ID is required for update");
            return;
        }

        shopRepository.updateShop(shop, callback);
    }

    /**
     * Delete a shop
     * @param shopId The shop ID to delete
     * @param callback Callback to handle success/failure
     */
    public void deleteShop(String shopId, ShopRepository.OnOperationCallback callback) {
        if (shopId == null || shopId.trim().isEmpty()) {
            callback.onFailure("Shop ID cannot be empty");
            return;
        }
        shopRepository.deleteShop(shopId, callback);
    }

    /**
     * Validate shop data
     * @param shop The shop to validate
     * @return Error message if validation fails, null if valid
     */
    private String validateShop(Shop shop) {
        if (shop == null) {
            return "Shop data cannot be null";
        }

        if (shop.getStoreName() == null || shop.getStoreName().trim().isEmpty()) {
            return "Tên cửa hàng không được để trống";
        }

        if (shop.getBusinessType() == null || shop.getBusinessType().trim().isEmpty()) {
            return "Loại hình kinh doanh không được để trống";
        }

        if (shop.getAddress() == null || shop.getAddress().trim().isEmpty()) {
            return "Địa chỉ không được để trống";
        }

        if (shop.getRepresentativeName() == null || shop.getRepresentativeName().trim().isEmpty()) {
            return "Tên người đại diện không được để trống";
        }

        if (shop.getPhoneNumber() == null || shop.getPhoneNumber().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }

        if (!shop.getPhoneNumber().matches("^[0-9]{10,11}$")) {
            return "Số điện thoại không hợp lệ";
        }

        if (shop.getEmail() == null || shop.getEmail().trim().isEmpty()) {
            return "Email không được để trống";
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(shop.getEmail()).matches()) {
            return "Email không hợp lệ";
        }

        return null;
    }

    /**
     * Validate shop ID number
     * @param idNumber The ID number to validate
     * @return Error message if validation fails, null if valid
     */
    public String validateIdNumber(String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            return "Số CMND/CCCD không được để trống";
        }

        if (!idNumber.matches("^[0-9]{9,12}$")) {
            return "Số CMND/CCCD không hợp lệ";
        }

        return null;
    }

    /**
     * Validate bank account
     * @param bankAccount The bank account to validate
     * @return Error message if validation fails, null if valid
     */
    public String validateBankAccount(String bankAccount) {
        if (bankAccount == null || bankAccount.trim().isEmpty()) {
            return "Số tài khoản ngân hàng không được để trống";
        }

        if (!bankAccount.matches("^[0-9]{8,20}$")) {
            return "Số tài khoản ngân hàng không hợp lệ";
        }

        return null;
    }
}

