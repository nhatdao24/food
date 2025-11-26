package com.example.appfood.store.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.store.controller.DishController;
import com.example.appfood.store.model.Dish;
import com.example.appfood.store.repository.DishRepository;
import com.example.appfood.store.view.adapter.DishAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * View class for displaying list of dishes
 * Uses DishController for business logic
 */
public class DishListActivity extends AppCompatActivity {

    private TextView btnBack;
    private Button btnAddDish;
    private LinearLayout navHome;
    private LinearLayout navNotifications;
    private LinearLayout navProfile;
    private RecyclerView rvDishList;
    private DishAdapter dishAdapter;
    private ArrayList<Dish> dishList;

    private DishController dishController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_dish_list);

        dishController = DishController.getInstance();

        initViews();
        setupListeners();
        setupRecyclerView();
        fetchDishes();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnAddDish = findViewById(R.id.btnAddDish);
        navHome = findViewById(R.id.navHome);
        navNotifications = findViewById(R.id.navNotifications);
        navProfile = findViewById(R.id.navProfile);
        rvDishList = findViewById(R.id.rvDishList);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnAddDish.setOnClickListener(v -> {
            Intent intent = new Intent(DishListActivity.this, AddDishActivity.class);
            startActivity(intent);
        });

        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(DishListActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        navNotifications.setOnClickListener(v -> {
            // TODO: Navigate to Notifications
        });

        navProfile.setOnClickListener(v -> {
            // TODO: Navigate to Profile
        });
    }

    private void setupRecyclerView() {
        rvDishList.setLayoutManager(new LinearLayoutManager(this));
        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList);
        rvDishList.setAdapter(dishAdapter);
    }

    private void fetchDishes() {
        dishController.getAllDishes(new DishRepository.OnDataLoadedCallback<List<Dish>>() {
            @Override
            public void onSuccess(List<Dish> dishes) {
                dishList.clear();
                dishList.addAll(dishes);
                dishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(DishListActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list when coming back from Add/Edit
        fetchDishes();
    }
}
