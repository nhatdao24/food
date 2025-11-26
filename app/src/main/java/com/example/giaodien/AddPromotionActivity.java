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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddPromotionActivity extends AppCompatActivity {
    private TextView btnBack;
    private EditText etVoucherName, etDiscountValue, etCondition, etEndDate, etQuantity;
    private Spinner spinnerStatus;
    private Button btnComplete;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initViews();
        setupListeners();
        setupStatusSpinner();
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

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnComplete.setOnClickListener(v -> {
            if (validateInput()) {
                savePromotion();
            }
        });
    }

    private void setupStatusSpinner() {
        String[] statusOptions = {"Còn hiệu lực", "Hết hiệu lực"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private boolean validateInput() {
        if (etVoucherName.getText().toString().trim().isEmpty()) {
            etVoucherName.setError("Vui lòng nhập tên voucher");
            return false;
        }
        if (etDiscountValue.getText().toString().trim().isEmpty()) {
            etDiscountValue.setError("Vui lòng nhập giá trị giảm");
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
        int status = spinnerStatus.getSelectedItemPosition(); // 0 for Active, 1 for Inactive

        HashMap<String, Object> promotionInfo = new HashMap<>();
        promotionInfo.put("voucherName", voucherName);
        promotionInfo.put("discountValue", discountValue);
        promotionInfo.put("condition", condition);
        promotionInfo.put("endDate", endDate);
        promotionInfo.put("quantity", quantity);
        promotionInfo.put("status", status);

        String promotionId = mDatabase.child("promotions").push().getKey();

        if (promotionId != null) {
            mDatabase.child("promotions").child(promotionId).setValue(promotionInfo)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddPromotionActivity.this, "Đã thêm khuyến mãi thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(AddPromotionActivity.this, "Thêm khuyến mãi thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Thêm khuyến mãi thất bại: không thể tạo promotion id.", Toast.LENGTH_SHORT).show();
        }
    }
}
