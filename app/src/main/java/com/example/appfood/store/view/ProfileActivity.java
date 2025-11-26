package com.example.appfood.store.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.store.controller.ShopController;
import com.example.appfood.store.model.Shop;
import com.example.appfood.store.repository.ShopRepository;

/**
 * ProfileActivity - Hiển thị thông tin hồ sơ cửa hàng
 */
public class ProfileActivity extends AppCompatActivity {

    // UI Components
    private ImageButton btnBack;
    private ImageView ivAvatar;
    private EditText etFullName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etCMNDCCCD;
    private EditText etIdNumber;
    private EditText etAccountNumber;
    private EditText etBusinessType;
    private EditText etBusinessCategory;
    private EditText etStoreAddress;
    private Button btnLogout;

    // Controllers
    private ShopController shopController;

    // UI State
    private ProgressDialog progressDialog;

    // Data
    private Shop currentShop;
    private String testShopId = "test_shop_001"; // For testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_profile);

        initializeViews();
        initializeControllers();
        setupListeners();
        loadProfileData();
    }

    /**
     * Khởi tạo các view components
     */
    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        ivAvatar = findViewById(R.id.ivAvatar);
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etCMNDCCCD = findViewById(R.id.etCMNDCCCD);
        etIdNumber = findViewById(R.id.etIdNumber);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etBusinessType = findViewById(R.id.etBusinessType);
        etBusinessCategory = findViewById(R.id.etBusinessCategory);
        etStoreAddress = findViewById(R.id.etStoreAddress);
        btnLogout = findViewById(R.id.btnLogout);
    }

    /**
     * Khởi tạo controllers
     */
    private void initializeControllers() {
        shopController = ShopController.getInstance();

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setCancelable(false);
    }

    /**
     * Thiết lập các event listeners
     */
    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Logout button
        btnLogout.setOnClickListener(v -> handleLogout());

        // Avatar click for changing image (future feature)
        ivAvatar.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this,
                "Chức năng đổi ảnh đại diện sẽ được cập nhật sau",
                Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Load dữ liệu profile từ database
     */
    private void loadProfileData() {
        // Show progress
        progressDialog.show();

        // TODO: Get shopId from login session
        // For now, using test shop ID
        String shopId = testShopId;

        // Load shop data
        shopController.getShopById(shopId, new ShopRepository.OnDataLoadedCallback<Shop>() {
            @Override
            public void onSuccess(Shop shop) {
                progressDialog.dismiss();
                currentShop = shop;
                displayProfileData(shop);
            }

            @Override
            public void onFailure(String errorMessage) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this,
                    "Lỗi: " + errorMessage,
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Hiển thị dữ liệu lên UI
     */
    private void displayProfileData(Shop shop) {
        if (shop == null) {
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị họ tên (Representative Name)
        if (shop.getRepresentativeName() != null && !shop.getRepresentativeName().isEmpty()) {
            etFullName.setText(shop.getRepresentativeName());
        } else {
            etFullName.setText("-");
        }

        // Hiển thị số điện thoại
        if (shop.getPhoneNumber() != null && !shop.getPhoneNumber().isEmpty()) {
            etPhone.setText(shop.getPhoneNumber());
        } else {
            etPhone.setText("-");
        }

        // Hiển thị email
        if (shop.getEmail() != null && !shop.getEmail().isEmpty()) {
            etEmail.setText(shop.getEmail());
        } else {
            etEmail.setText("-");
        }

        // Hiển thị CMND/CCCD
        if (shop.getIdNumber() != null && !shop.getIdNumber().isEmpty()) {
            etCMNDCCCD.setText(shop.getIdNumber());
        } else {
            etCMNDCCCD.setText("-");
        }

        // Hiển thị Tên tài khoản (Account Holder)
        if (shop.getAccountHolder() != null && !shop.getAccountHolder().isEmpty()) {
            etIdNumber.setText(shop.getAccountHolder());
        } else {
            etIdNumber.setText("-");
        }

        // Hiển thị Số tài khoản
        if (shop.getBankAccount() != null && !shop.getBankAccount().isEmpty()) {
            etAccountNumber.setText(shop.getBankAccount());
        } else {
            etAccountNumber.setText("-");
        }

        // Hiển thị tên cửa hàng
        if (shop.getStoreName() != null && !shop.getStoreName().isEmpty()) {
            etBusinessType.setText(shop.getStoreName());
        } else {
            etBusinessType.setText("-");
        }

        // Hiển thị loại hình kinh doanh
        if (shop.getBusinessType() != null && !shop.getBusinessType().isEmpty()) {
            etBusinessCategory.setText(shop.getBusinessType());
        } else {
            etBusinessCategory.setText("-");
        }

        // Hiển thị địa chỉ cửa hàng
        if (shop.getAddress() != null && !shop.getAddress().isEmpty()) {
            etStoreAddress.setText(shop.getAddress());
        } else {
            etStoreAddress.setText("-");
        }

        // TODO: Load avatar from Firebase Storage
        // if (shop.getAvatarUrl() != null && !shop.getAvatarUrl().isEmpty()) {
        //     Glide.with(this)
        //         .load(shop.getAvatarUrl())
        //         .placeholder(R.drawable.ic_avatar_placeholder)
        //         .error(R.drawable.ic_avatar_placeholder)
        //         .into(ivAvatar);
        // }
    }

    /**
     * Xử lý đăng xuất
     */
    private void handleLogout() {
        // Show confirmation dialog
        new AlertDialog.Builder(this)
            .setTitle("Đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất?")
            .setPositiveButton("Đăng xuất", (dialog, which) -> performLogout())
            .setNegativeButton("Hủy", null)
            .show();
    }

    /**
     * Thực hiện đăng xuất
     */
    private void performLogout() {
        // TODO: Clear session data
        Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();

        // Navigate back
        finish();
    }

    /**
     * Enable/Disable editing mode
     */
    public void enableEditing(boolean enable) {
        etFullName.setEnabled(enable);
        etPhone.setEnabled(enable);
        etEmail.setEnabled(enable);
        etCMNDCCCD.setEnabled(enable);
        etIdNumber.setEnabled(enable);
        etAccountNumber.setEnabled(enable);
        etBusinessType.setEnabled(enable);
        etBusinessCategory.setEnabled(enable);
        etStoreAddress.setEnabled(enable);
    }
}

