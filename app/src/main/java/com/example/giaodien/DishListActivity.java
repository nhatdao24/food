package com.example.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DishListActivity extends AppCompatActivity {

    private TextView btnBack;
    private Button btnAddDish;
    private LinearLayout navHome;
    private LinearLayout navNotifications;
    private LinearLayout navProfile;
    private RecyclerView rvDishList;
    private DishAdapter dishAdapter;
    private ArrayList<Dish> dishList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishListActivity.this, AddDishActivity.class);
                startActivity(intent);
            }
        });

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishListActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to Notifications
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Navigate to Profile
            }
        });
    }

    private void setupRecyclerView() {
        rvDishList.setLayoutManager(new LinearLayoutManager(this));
        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList);
        rvDishList.setAdapter(dishAdapter);
    }

    private void fetchDishes() {
        mDatabase = FirebaseDatabase.getInstance().getReference("dishes");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dishList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dish dish = dataSnapshot.getValue(Dish.class);
                    if (dish != null) {
                        dish.setDishId(dataSnapshot.getKey());
                        dishList.add(dish);
                    }
                }
                dishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
