package com.example.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // Initialize views
        initViews();

        // Setup spinner
        setupBusinessTypeSpinner();

        // Setup button click listener
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Navigate to step 2
                    Intent intent = new Intent(RegisterStep1Activity.this, RegisterStep2Activity.class);

                    // Pass data to next activity
                    intent.putExtra("store_name", etStoreName.getText().toString());
                    intent.putExtra("business_type", spinnerBusinessType.getSelectedItem().toString());
                    intent.putExtra("address", etAddress.getText().toString());
                    intent.putExtra("representative_name", etRepresentativeName.getText().toString());
                    intent.putExtra("phone_number", etPhoneNumber.getText().toString());
                    intent.putExtra("email", etEmail.getText().toString());

                    startActivity(intent);
                }
            }
        });
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
        // Create array of business types
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

    private boolean validateInputs() {
        // Validate store name
        if (etStoreName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên cửa hàng", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate business type
        if (spinnerBusinessType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn loại hình kinh doanh", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate address
        if (etAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate representative name
        if (etRepresentativeName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên người đại diện", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate phone number
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!phoneNumber.matches("^[0-9]{10,11}$")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate email
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

