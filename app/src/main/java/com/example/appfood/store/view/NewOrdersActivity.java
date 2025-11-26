package com.example.appfood.store.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;

/**
 * View class for New Orders (standalone activity)
 * TODO: Remove if using fragments only
 */
public class NewOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_new_orders);
    }
}
