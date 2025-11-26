package com.example.appfood.store.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Sales Report
 * TODO: Implement with proper charts and data when ready
 */
public class SalesReportActivity extends AppCompatActivity {

    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

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
