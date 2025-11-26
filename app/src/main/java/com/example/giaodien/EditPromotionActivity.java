package com.example.giaodien;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditPromotionActivity extends AppCompatActivity {

    private TextView btnBack;
    private EditText etVoucherName, etDiscountValue, etCondition, etEndDate, etQuantity;
    private Spinner spinnerStatus;
    private Button btnComplete, btnDelete;

    private DatabaseReference mDatabase;
    private String promotionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion);

        promotionId = getIntent().getStringExtra("promotionId");
        if (promotionId == null) {
            Toast.makeText(this, "Không có ID khuyến mãi", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);

        initViews();
        setupListeners();
        setupStatusSpinner();
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

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnComplete.setOnClickListener(v -> updatePromotion());
        btnDelete.setOnClickListener(v -> deletePromotion());
    }

    private void setupStatusSpinner() {
        String[] statusOptions = {"Còn hiệu lực", "Hết hiệu lực"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void loadPromotionData() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Promotion promotion = snapshot.getValue(Promotion.class);
                if (promotion != null) {
                    etVoucherName.setText(promotion.getVoucherName() != null ? promotion.getVoucherName() : "");
                    etDiscountValue.setText(promotion.getDiscountValue() != null ? promotion.getDiscountValue() : "");
                    etCondition.setText(promotion.getCondition() != null ? promotion.getCondition() : "");
                    etEndDate.setText(promotion.getEndDate() != null ? promotion.getEndDate() : "");
                    etQuantity.setText(String.valueOf(promotion.getQuantity()));
                    
                    if (promotion.getStatus() >= 0 && promotion.getStatus() < spinnerStatus.getCount()) {
                        spinnerStatus.setSelection(promotion.getStatus());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditPromotionActivity.this, "Failed to load promotion data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePromotion() {
        String voucherName = etVoucherName.getText().toString().trim();
        String discountValue = etDiscountValue.getText().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        long quantity = 0;
        if (!etQuantity.getText().toString().trim().isEmpty()){
           quantity = Long.parseLong(etQuantity.getText().toString().trim());
        }
        int status = spinnerStatus.getSelectedItemPosition();

        HashMap<String, Object> promotionInfo = new HashMap<>();
        promotionInfo.put("voucherName", voucherName);
        promotionInfo.put("discountValue", discountValue);
        promotionInfo.put("condition", condition);
        promotionInfo.put("endDate", endDate);
        promotionInfo.put("quantity", quantity);
        promotionInfo.put("status", status);

        mDatabase.updateChildren(promotionInfo)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditPromotionActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditPromotionActivity.this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void deletePromotion() {
        mDatabase.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditPromotionActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditPromotionActivity.this, "Xóa thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
