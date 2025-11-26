package com.example.appfood.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.controller.PromotionController;
import com.example.appfood.model.Promotion;
import com.example.appfood.repository.PromotionRepository;

/**
 * View class for editing promotion
 * Uses PromotionController for business logic
 */
public class EditPromotionActivity extends AppCompatActivity {

    private TextView btnBack;
    private EditText etVoucherName, etDiscountValue, etCondition, etEndDate, etQuantity;
    private Spinner spinnerStatus;
    private Button btnComplete, btnDelete;

    private PromotionController promotionController;
    private String promotionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion);

        promotionController = PromotionController.getInstance();
        promotionId = getIntent().getStringExtra("promotionId");

        initViews();
        setupStatusSpinner();
        setupListeners();
        loadPromotionData();
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
        btnDelete = findViewById(R.id.btnDelete);
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

        btnComplete.setOnClickListener(v -> updatePromotion());

        btnDelete.setOnClickListener(v -> deletePromotion());
    }

    private void loadPromotionData() {
        promotionController.getPromotionById(promotionId, new PromotionRepository.OnDataLoadedCallback<Promotion>() {
            @Override
            public void onSuccess(Promotion promotion) {
                displayPromotion(promotion);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditPromotionActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayPromotion(Promotion promotion) {
        etVoucherName.setText(promotion.getVoucherName());
        etDiscountValue.setText(promotion.getDiscountValue());
        etCondition.setText(promotion.getCondition());
        etEndDate.setText(promotion.getEndDate());
        etQuantity.setText(String.valueOf(promotion.getQuantity()));
        spinnerStatus.setSelection(promotion.getStatus());
    }

    private void updatePromotion() {
        String voucherName = etVoucherName.getText().toString().trim();
        String discountValue = etDiscountValue.getText().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        long quantity = Long.parseLong(etQuantity.getText().toString().trim());
        int status = spinnerStatus.getSelectedItemPosition();

        Promotion promotion = new Promotion();
        promotion.setPromotionId(promotionId);
        promotion.setVoucherName(voucherName);
        promotion.setDiscountValue(discountValue);
        promotion.setCondition(condition);
        promotion.setEndDate(endDate);
        promotion.setQuantity(quantity);
        promotion.setStatus(status);

        promotionController.updatePromotion(promotion, new PromotionRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditPromotionActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditPromotionActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePromotion() {
        promotionController.deletePromotion(promotionId, new PromotionRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditPromotionActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditPromotionActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

