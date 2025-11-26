package com.example.giaodien;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SalesReportActivity extends AppCompatActivity {
    private TextView btnBack;
    private TextView tabToday, tabWeek, tabMonth;
    private TextView tvRevenue, tvProductTotal, tvTotalAmount;
    private String currentTab = "today";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);
        initViews();
        setupListeners();
        loadTodayReport();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tabToday = findViewById(R.id.tabToday);
        tabWeek = findViewById(R.id.tabWeek);
        tabMonth = findViewById(R.id.tabMonth);
        tvRevenue = findViewById(R.id.tvRevenue);
        tvProductTotal = findViewById(R.id.tvProductTotal);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        tabToday.setOnClickListener(v -> {
            switchTab("today");
            loadTodayReport();
        });

        tabWeek.setOnClickListener(v -> {
            switchTab("week");
            loadWeekReport();
        });

        tabMonth.setOnClickListener(v -> {
            switchTab("month");
            loadMonthReport();
        });
    }

    private void switchTab(String tab) {
        currentTab = tab;

        tabToday.setTextColor(getResources().getColor(
            tab.equals("today") ? R.color.primary_orange : R.color.text_secondary, null));
        tabWeek.setTextColor(getResources().getColor(
            tab.equals("week") ? R.color.primary_orange : R.color.text_secondary, null));
        tabMonth.setTextColor(getResources().getColor(
            tab.equals("month") ? R.color.primary_orange : R.color.text_secondary, null));

        tabToday.setTypeface(null, tab.equals("today") ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
        tabWeek.setTypeface(null, tab.equals("week") ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
        tabMonth.setTypeface(null, tab.equals("month") ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
    }

    private void loadTodayReport() {
        tvRevenue.setText("");
        tvProductTotal.setText("");
        tvTotalAmount.setText("");
    }

    private void loadWeekReport() {
        tvRevenue.setText("");
        tvProductTotal.setText("");
        tvTotalAmount.setText("");
    }

    private void loadMonthReport() {
        tvRevenue.setText("");
        tvProductTotal.setText("");
        tvTotalAmount.setText("");
    }
}

