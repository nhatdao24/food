package com.example.appfood.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Shop Registration - Step 1
 * Collects basic shop information
 */
public class RegisterStep1Activity extends AppCompatActivity {

    private EditText etStoreName;
    private Spinner spinnerBusinessType;
    private EditText etAddress;
    private EditText etRepresentativeName;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);

        initViews();
        setupBusinessTypeSpinner();
        setupListeners();
    }

    private void initViews() {
        etStoreName = findViewById(R.id.etStoreName);
        spinnerBusinessType = findViewById(R.id.spinnerBusinessType);
        etAddress = findViewById(R.id.etAddress);
        etRepresentativeName = findViewById(R.id.etRepresentativeName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setupBusinessTypeSpinner() {
        String[] businessTypes = {
            "Chọn loại hình kinh doanh",
            "Nhà hàng",
            "Quán ăn",
            "Quán cafe",
            "Quán trà sữa",
            "Cửa hàng thức ăn nhanh",
            "Cửa hàng bánh ngọt",
            "Khác"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            businessTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBusinessType.setAdapter(adapter);
    }

    private void setupListeners() {
        btnContinue.setOnClickListener(v -> {
            if (validateInputs()) {
                navigateToStep2();
            }
        });
    }

    private boolean validateInputs() {
        if (etStoreName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên cửa hàng", Toast.LENGTH_SHORT).show();
            etStoreName.requestFocus();
            return false;
        }

        if (spinnerBusinessType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn loại hình kinh doanh", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
            etAddress.requestFocus();
            return false;
        }

        if (etRepresentativeName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên người đại diện", Toast.LENGTH_SHORT).show();
            etRepresentativeName.requestFocus();
            return false;
        }

        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return false;
        }
        if (!phoneNumber.matches("^[0-9]{10,11}$")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return false;
        }

        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void navigateToStep2() {
        Intent intent = new Intent(RegisterStep1Activity.this, RegisterStep2Activity.class);

        // Pass data to next activity
        intent.putExtra("store_name", etStoreName.getText().toString().trim());
        intent.putExtra("business_type", spinnerBusinessType.getSelectedItem().toString());
        intent.putExtra("address", etAddress.getText().toString().trim());
        intent.putExtra("representative_name", etRepresentativeName.getText().toString().trim());
        intent.putExtra("phone_number", etPhoneNumber.getText().toString().trim());
        intent.putExtra("email", etEmail.getText().toString().trim());

        startActivity(intent);
    }
}

