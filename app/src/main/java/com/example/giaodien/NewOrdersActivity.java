package com.example.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrdersActivity extends AppCompatActivity {

    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

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

        Button btnCancelOrder1 = findViewById(R.id.btnCancelOrder1);
        Button btnConfirmOrder1 = findViewById(R.id.btnConfirmOrder1);

        btnCancelOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirmOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình đơn hàng đã xác nhận
                Intent intent = new Intent(NewOrdersActivity.this, ConfirmedOrdersActivity.class);
                startActivity(intent);
            }
        });

        Button btnCancelOrder2 = findViewById(R.id.btnCancelOrder2);
        Button btnConfirmOrder2 = findViewById(R.id.btnConfirmOrder2);

        btnCancelOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirmOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewOrdersActivity.this, ConfirmedOrdersActivity.class);
                startActivity(intent);
            }
        });

        Button btnCancelOrder3 = findViewById(R.id.btnCancelOrder3);
        Button btnConfirmOrder3 = findViewById(R.id.btnConfirmOrder3);

        btnCancelOrder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirmOrder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewOrdersActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewOrdersActivity.this, ConfirmedOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}

