package com.example.giaodien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmedOrdersActivity extends AppCompatActivity {

    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_orders);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnPreparing1 = findViewById(R.id.btnPreparing1);
        btnPreparing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmedOrdersActivity.this, "Đơn hàng đang được chuẩn bị", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPreparing2 = findViewById(R.id.btnPreparing2);
        btnPreparing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmedOrdersActivity.this, "Đơn hàng đang được chuẩn bị", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPreparing3 = findViewById(R.id.btnPreparing3);
        btnPreparing3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmedOrdersActivity.this, "Đơn hàng đang được chuẩn bị", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

