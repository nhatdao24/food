package com.example.appfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterStep2Activity extends AppCompatActivity {

    private EditText etIdNumber;
    private Spinner spinnerIssueDate;
    private EditText etIssuePlace;
    private EditText etBankName;
    private EditText etBankAccount;
    private EditText etAccountHolder;
    private EditText etBankBranch;
    private Button btnComplete;
    private DatabaseReference mDatabase;

    private String storeName;
    private String businessType;
    private String address;
    private String representativeName;
    private String phoneNumber;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get data from Intent
        Intent intent = getIntent();
        storeName = intent.getStringExtra("store_name");
        businessType = intent.getStringExtra("business_type");
        address = intent.getStringExtra("address");
        representativeName = intent.getStringExtra("representative_name");
        phoneNumber = intent.getStringExtra("phone_number");
        email = intent.getStringExtra("email");

        // Initialize views
        initViews();

        // Setup spinner
        setupIssueDateSpinner();

        // Setup button click listener
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    completeRegistration();
                }
            }
        });
    }

    private void initViews() {
        etIdNumber = findViewById(R.id.etIdNumber);
        spinnerIssueDate = findViewById(R.id.spinnerIssueDate);
        etIssuePlace = findViewById(R.id.etIssuePlace);
        etBankName = findViewById(R.id.etBankName);
        etBankAccount = findViewById(R.id.etBankAccount);
        etAccountHolder = findViewById(R.id.etAccountHolder);
        etBankBranch = findViewById(R.id.etBankBranch);
        btnComplete = findViewById(R.id.btnComplete);
    }

    private void setupIssueDateSpinner() {
        // Create array of sample dates
        String[] dates = {
            "Chọn ngày cấp",
            "01/01/2020",
            "15/03/2021",
            "20/06/2022",
            "10/09/2023",
            "05/12/2024"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            dates
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIssueDate.setAdapter(adapter);
    }

    private boolean validateInputs() {
        // Validate ID number
        String idNumber = etIdNumber.getText().toString().trim();
        if (idNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số CMND/CCCD", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!idNumber.matches("^[0-9]{9,12}$")) {
            Toast.makeText(this, "Số CMND/CCCD không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate issue date
        if (spinnerIssueDate.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày cấp", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate issue place
        if (etIssuePlace.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập nơi cấp", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate bank name
        if (etBankName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên ngân hàng", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate bank account
        String bankAccount = etBankAccount.getText().toString().trim();
        if (bankAccount.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số tài khoản ngân hàng", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate account holder
        if (etAccountHolder.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên chủ tài khoản", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate bank branch
        if (etBankBranch.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập chi nhánh ngân hàng", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void completeRegistration() {
        String idNumber = etIdNumber.getText().toString().trim();
        String issueDate = spinnerIssueDate.getSelectedItem().toString();
        String issuePlace = etIssuePlace.getText().toString().trim();
        String bankName = etBankName.getText().toString().trim();
        String bankAccount = etBankAccount.getText().toString().trim();
        String accountHolder = etAccountHolder.getText().toString().trim();
        String bankBranch = etBankBranch.getText().toString().trim();

        HashMap<String, Object> storeInfo = new HashMap<>();
        // Data from Step 1
        storeInfo.put("storeName", storeName);
        storeInfo.put("businessType", businessType);
        storeInfo.put("address", address);
        storeInfo.put("representativeName", representativeName);
        storeInfo.put("phoneNumber", phoneNumber);
        storeInfo.put("email", email);

        // Data from Step 2
        storeInfo.put("idNumber", idNumber);
        storeInfo.put("issueDate", issueDate);
        storeInfo.put("issuePlace", issuePlace);
        storeInfo.put("bankName", bankName);
        storeInfo.put("bankAccount", bankAccount);
        storeInfo.put("accountHolder", accountHolder);
        storeInfo.put("bankBranch", bankBranch);

        String storeId = mDatabase.child("stores").push().getKey();

        if (storeId != null) {
            mDatabase.child("stores").child(storeId).setValue(storeInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterStep2Activity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterStep2Activity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterStep2Activity.this, "Đăng ký thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Đăng ký thất bại: không thể tạo store id.", Toast.LENGTH_LONG).show();
        }
    }
}
