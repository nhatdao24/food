package com.example.appfood.store.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Review Detail
 * TODO: Implement with ReviewController when ready
 */
public class ReviewDetailActivity extends AppCompatActivity {

    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}
