package com.example.appfood.store.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.store.controller.DishController;
import com.example.appfood.store.model.Dish;
import com.example.appfood.store.repository.DishRepository;

/**
 * View class for adding new dish
 * Uses DishController for business logic
 */
public class AddDishActivity extends AppCompatActivity {

    private TextView btnBack;
    private LinearLayout btnChooseImage;
    private EditText etDishName;
    private EditText etQuantity;
    private EditText etPrice;
    private EditText etDishType;
    private EditText etRestaurant;
    private EditText etDescription;
    private Button btnComplete;

    private DishController dishController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_add_dish);

        dishController = DishController.getInstance();

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        etDishName = findViewById(R.id.etDishName);
        etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);
        etDishType = findViewById(R.id.etDishType);
        etRestaurant = findViewById(R.id.etRestaurant);
        etDescription = findViewById(R.id.etDescription);
        btnComplete = findViewById(R.id.btnComplete);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnChooseImage.setOnClickListener(v -> {
            // TODO: Open image picker
            Toast.makeText(AddDishActivity.this, "Chọn hình ảnh", Toast.LENGTH_SHORT).show();
        });

        btnComplete.setOnClickListener(v -> {
            if (validateInputs()) {
                saveDish();
            }
        });
    }

    private boolean validateInputs() {
        if (etDishName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
            etDishName.requestFocus();
            return false;
        }

        if (etQuantity.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            etQuantity.requestFocus();
            return false;
        }

        if (etPrice.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            etPrice.requestFocus();
            return false;
        }

        if (etDishType.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập loại món ăn", Toast.LENGTH_SHORT).show();
            etDishType.requestFocus();
            return false;
        }

        if (etRestaurant.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin quán", Toast.LENGTH_SHORT).show();
            etRestaurant.requestFocus();
            return false;
        }

        return true;
    }

    private void saveDish() {
        String dishName = etDishName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String dishType = etDishType.getText().toString().trim();
        String restaurant = etRestaurant.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Create Dish object
        Dish dish = new Dish(dishName, quantity, price, dishType, restaurant, description);

        // Use DishController to create dish
        dishController.createDish(dish, new DishRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(AddDishActivity.this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(AddDishActivity.this, "Thêm món ăn thất bại: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
