package com.example.appfood;

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
        // Wrap entire onCreate in a try-catch block to prevent any crash.
        try {
            setContentView(R.layout.activity_edit_promotion);

            promotionId = getIntent().getStringExtra("promotionId");
            if (promotionId == null || promotionId.isEmpty()) {
                Toast.makeText(this, "Không có ID khuyến mãi", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            mDatabase = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);

            initViews();
            setupListeners();
            setupStatusSpinner();
            loadPromotionData();
        } catch (Exception e) {
            Toast.makeText(this, "Đã xảy ra lỗi nghiêm trọng khi mở màn hình: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
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
                try {
                    if (!snapshot.exists()) {
                        Toast.makeText(EditPromotionActivity.this, "Không tìm thấy khuyến mãi.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }

                    // Field: voucherName (new) vs promotionName (old)
                    String voucherName = "";
                    if (snapshot.child("voucherName").exists()) {
                        voucherName = snapshot.child("voucherName").getValue(String.class);
                    } else if (snapshot.child("promotionName").exists()) {
                        voucherName = snapshot.child("promotionName").getValue(String.class);
                    }

                    // Field: discountValue
                    String discountValue = "";
                    if (snapshot.child("discountValue").exists()) {
                        discountValue = String.valueOf(snapshot.child("discountValue").getValue());
                    }

                    // Field: condition
                    String condition = "";
                    if (snapshot.child("condition").exists()) {
                        condition = String.valueOf(snapshot.child("condition").getValue());
                    }

                    // Field: endDate
                    String endDate = "";
                    if (snapshot.child("endDate").exists()) {
                        endDate = snapshot.child("endDate").getValue(String.class);
                    }

                    // Field: quantity (ULTRA SAFE PARSING)
                    long quantity = 0;
                    if (snapshot.child("quantity").exists()) {
                        Object quantityObj = snapshot.child("quantity").getValue();
                        if (quantityObj instanceof Number) {
                            quantity = ((Number) quantityObj).longValue();
                        } else if (quantityObj instanceof String) {
                            String quantityStr = (String) quantityObj;
                            if (!quantityStr.isEmpty()) {
                                try {
                                    quantity = Long.parseLong(quantityStr);
                                } catch (NumberFormatException e) { /* Keep default 0 */ }
                            }
                        }
                    }

                    // Field: status (ULTRA SAFE PARSING)
                    int status = 0;
                    if (snapshot.child("status").exists()) {
                        Object statusObj = snapshot.child("status").getValue();
                        if (statusObj instanceof Number) {
                            status = ((Number) statusObj).intValue();
                        } else if (statusObj instanceof String) {
                             String statusStr = (String) statusObj;
                            if (!statusStr.isEmpty()) {
                                try {
                                    status = Integer.parseInt(statusStr);
                                } catch (NumberFormatException e) { /* Keep default 0 */ }
                            }
                        }
                    }

                    // Set values to UI
                    etVoucherName.setText(voucherName);
                    etDiscountValue.setText(discountValue);
                    etCondition.setText(condition);
                    etEndDate.setText(endDate);
                    etQuantity.setText(String.valueOf(quantity));

                    if (status >= 0 && status < spinnerStatus.getCount()) {
                        spinnerStatus.setSelection(status);
                    }

                } catch (Exception e) {
                    Toast.makeText(EditPromotionActivity.this, "Lỗi đọc dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditPromotionActivity.this, "Không thể tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePromotion() {
        String voucherName = etVoucherName.getText().toString().trim();
        String discountValue = etDiscountValue.getText().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        
        long quantity = 0;
        String quantityStr = etQuantity.getText().toString().trim();
        if (!quantityStr.isEmpty()) {
            try {
                quantity = Long.parseLong(quantityStr);
            } catch (NumberFormatException e) {
                etQuantity.setError("Vui lòng nhập số hợp lệ");
                return;
            }
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
