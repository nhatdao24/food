package com.example.appfood.store.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - Entry point of the app
 * Redirects to RegisterStep1Activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Navigate directly to RegisterStep1Activity
        Intent intent = new Intent(this, RegisterStep1Activity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }
}

