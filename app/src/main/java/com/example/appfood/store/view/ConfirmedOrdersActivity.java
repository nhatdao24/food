package com.example.appfood.store.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for Confirmed Orders (standalone activity)
 * TODO: Remove if using fragments only
 */
public class ConfirmedOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_orders);
    }
}
