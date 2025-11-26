package com.example.appfood;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewDetailActivity extends AppCompatActivity {
    private TextView btnBack;
    private EditText etProductName, etQuantity, etComment, etResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        initViews();
        setupListeners();
        loadReviewData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etProductName = findViewById(R.id.etProductName);
        etQuantity = findViewById(R.id.etQuantity);
        etComment = findViewById(R.id.etComment);
        etResponse = findViewById(R.id.etResponse);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            if (!etResponse.getText().toString().trim().isEmpty()) {
                saveResponse();
            }
            finish();
        });
    }

    private void loadReviewData() {
        // TODO: Load review data from database/API
        etProductName.setText("Sample product name...");
        etQuantity.setText("2");
        etComment.setText("Sample customer comment...");
    }

    private void saveResponse() {
        String response = etResponse.getText().toString().trim();
        if (!response.isEmpty()) {
            Toast.makeText(this, "Đã lưu phản hồi", Toast.LENGTH_SHORT).show();
            // TODO: Save response to database/API
        }
    }
}

