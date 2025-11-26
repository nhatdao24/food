package com.example.appfood.store.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.store.controller.PromotionController;
import com.example.appfood.store.model.Promotion;
import com.example.appfood.store.repository.PromotionRepository;

/**
 * View class for adding new promotion
 * Uses PromotionController for business logic
 */
public class AddPromotionActivity extends AppCompatActivity {

    private TextView btnBack;
    private EditText etVoucherName, etDiscountValue, etCondition, etEndDate, etQuantity;
    private Spinner spinnerStatus;
    private Button btnComplete;

    private PromotionController promotionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);

        promotionController = PromotionController.getInstance();

        initViews();
        setupStatusSpinner();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etVoucherName = findViewById(R.id.etVoucherName);
        etDiscountValue = findViewById(R.id.etDiscountValue);
        etCondition = findViewById(R.id.etCondition);
        etEndDate = findViewById(R.id.etEndDate);
        etQuantity = findViewById(R.id.etQuantity);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnComplete = findViewById(R.id.btnComplete);
    }

    private void setupStatusSpinner() {
        String[] statuses = {"Còn hiệu lực", "Hết hiệu lực"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statuses
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnComplete.setOnClickListener(v -> {
            if (validateInputs()) {
                savePromotion();
            }
        });
    }

    private boolean validateInputs() {
        if (etVoucherName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên voucher", Toast.LENGTH_SHORT).show();
            etVoucherName.requestFocus();
            return false;
        }

        if (etDiscountValue.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá trị giảm giá", Toast.LENGTH_SHORT).show();
            etDiscountValue.requestFocus();
            return false;
        }

        if (etCondition.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập điều kiện", Toast.LENGTH_SHORT).show();
            etCondition.requestFocus();
            return false;
        }

        if (etEndDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập ngày hết hạn", Toast.LENGTH_SHORT).show();
            etEndDate.requestFocus();
            return false;
        }

        if (etQuantity.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            etQuantity.requestFocus();
            return false;
        }

        return true;
    }

    private void savePromotion() {
        String voucherName = etVoucherName.getText().toString().trim();
        String discountValue = etDiscountValue.getText().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        long quantity = Long.parseLong(etQuantity.getText().toString().trim());
        int status = spinnerStatus.getSelectedItemPosition();

        Promotion promotion = new Promotion();
        promotion.setVoucherName(voucherName);
        promotion.setDiscountValue(discountValue);
        promotion.setCondition(condition);
        promotion.setEndDate(endDate);
        promotion.setQuantity(quantity);
        promotion.setStatus(status);

        promotionController.createPromotion(promotion, new PromotionRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(AddPromotionActivity.this, "Thêm khuyến mãi thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(AddPromotionActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
