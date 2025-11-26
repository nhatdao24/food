package com.example.appfood.store.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.store.controller.ShopController;
import com.example.appfood.store.model.Shop;
import com.example.appfood.store.repository.ShopRepository;

/**
 * View class for Shop Registration - Step 2
 * Collects ID and bank information
 * Uses ShopController for business logic
 */
public class RegisterStep2Activity extends AppCompatActivity {

    private EditText etIdNumber;
    private Spinner spinnerIssueDate;
    private EditText etIssuePlace;
    private EditText etBankName;
    private EditText etBankAccount;
    private EditText etAccountHolder;
    private EditText etBankBranch;
    private Button btnComplete;

    private ShopController shopController;

    private String storeName;
    private String businessType;
    private String address;
    private String representativeName;
    private String phoneNumber;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_register_step2);

        shopController = ShopController.getInstance();

        // Get data from Intent
        Intent intent = getIntent();
        storeName = intent.getStringExtra("store_name");
        businessType = intent.getStringExtra("business_type");
        address = intent.getStringExtra("address");
        representativeName = intent.getStringExtra("representative_name");
        phoneNumber = intent.getStringExtra("phone_number");
        email = intent.getStringExtra("email");

        initViews();
        setupIssueDateSpinner();
        setupListeners();
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

    private void setupListeners() {
        btnComplete.setOnClickListener(v -> {
            if (validateInputs()) {
                completeRegistration();
            }
        });
    }

    private boolean validateInputs() {
        String idNumber = etIdNumber.getText().toString().trim();
        if (idNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số CMND/CCCD", Toast.LENGTH_SHORT).show();
            etIdNumber.requestFocus();
            return false;
        }
        if (!idNumber.matches("^[0-9]{9,12}$")) {
            Toast.makeText(this, "Số CMND/CCCD không hợp lệ", Toast.LENGTH_SHORT).show();
            etIdNumber.requestFocus();
            return false;
        }

        if (spinnerIssueDate.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày cấp", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etIssuePlace.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập nơi cấp", Toast.LENGTH_SHORT).show();
            etIssuePlace.requestFocus();
            return false;
        }

        if (etBankName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên ngân hàng", Toast.LENGTH_SHORT).show();
            etBankName.requestFocus();
            return false;
        }

        String bankAccount = etBankAccount.getText().toString().trim();
        if (bankAccount.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số tài khoản ngân hàng", Toast.LENGTH_SHORT).show();
            etBankAccount.requestFocus();
            return false;
        }

        if (etAccountHolder.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên chủ tài khoản", Toast.LENGTH_SHORT).show();
            etAccountHolder.requestFocus();
            return false;
        }

        if (etBankBranch.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập chi nhánh ngân hàng", Toast.LENGTH_SHORT).show();
            etBankBranch.requestFocus();
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

        // Create Shop object with all data
        Shop shop = new Shop(storeName, businessType, address, representativeName, phoneNumber, email);

        // Set ID document information
        shop.setIdNumber(idNumber);
        shop.setIssueDate(issueDate);
        shop.setIssuePlace(issuePlace);

        // Set bank information
        shop.setBankName(bankName);
        shop.setBankAccount(bankAccount);
        shop.setAccountHolder(accountHolder);
        shop.setBankBranch(bankBranch);

        shop.setStatus("active");

        // Use ShopController to create shop
        shopController.createShop(shop, new ShopRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterStep2Activity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterStep2Activity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(RegisterStep2Activity.this, "Đăng ký thất bại: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
