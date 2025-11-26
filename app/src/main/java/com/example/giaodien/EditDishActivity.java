package com.example.giaodien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditDishActivity extends AppCompatActivity {

    private TextView btnBack;
    private ImageView ivDishImage;
    private EditText etDishName;
    private EditText etQuantity;
    private EditText etPrice;
    private EditText etDishType;
    private EditText etRestaurant;
    private EditText etDescription;
    private Button btnComplete;
    private Button btnDelete;

    private DatabaseReference mDatabase;
    private String dishId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);

        dishId = getIntent().getStringExtra("dishId");
        mDatabase = FirebaseDatabase.getInstance().getReference("dishes").child(dishId);

        initViews();
        setupListeners();
        loadDishData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        ivDishImage = findViewById(R.id.ivDishImage);
        etDishName = findViewById(R.id.etDishName);
        etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);
        etDishType = findViewById(R.id.etDishType);
        etRestaurant = findViewById(R.id.etRestaurant);
        etDescription = findViewById(R.id.etDescription);
        btnComplete = findViewById(R.id.btnComplete);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnComplete.setOnClickListener(v -> updateDish());

        btnDelete.setOnClickListener(v -> deleteDish());
    }

    private void loadDishData() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dish dish = snapshot.getValue(Dish.class);
                if (dish != null) {
                    etDishName.setText(dish.getDishName());
                    etQuantity.setText(dish.getQuantity());
                    etPrice.setText(dish.getPrice());
                    etDishType.setText(dish.getDishType());
                    etRestaurant.setText(dish.getRestaurant());
                    etDescription.setText(dish.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditDishActivity.this, "Failed to load dish data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDish() {
        String dishName = etDishName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String dishType = etDishType.getText().toString().trim();
        String restaurant = etRestaurant.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        HashMap<String, Object> dishInfo = new HashMap<>();
        dishInfo.put("dishName", dishName);
        dishInfo.put("quantity", quantity);
        dishInfo.put("price", price);
        dishInfo.put("dishType", dishType);
        dishInfo.put("restaurant", restaurant);
        dishInfo.put("description", description);

        mDatabase.updateChildren(dishInfo)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditDishActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditDishActivity.this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void deleteDish() {
        mDatabase.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditDishActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditDishActivity.this, "Xóa thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
