package com.example.appfood.store.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.store.controller.DishController;
import com.example.appfood.store.model.Dish;
import com.example.appfood.store.repository.DishRepository;

/**
 * View class for editing dish
 * Uses DishController for business logic
 */
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

    private DishController dishController;
    private String dishId;
    private Dish currentDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_edit_dish);

        dishController = DishController.getInstance();
        dishId = getIntent().getStringExtra("dishId");

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
        dishController.getDishById(dishId, new DishRepository.OnDataLoadedCallback<Dish>() {
            @Override
            public void onSuccess(Dish dish) {
                currentDish = dish;
                displayDish(dish);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditDishActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayDish(Dish dish) {
        etDishName.setText(dish.getDishName());
        etQuantity.setText(dish.getQuantity());
        etPrice.setText(dish.getPrice());
        etDishType.setText(dish.getDishType());
        etRestaurant.setText(dish.getRestaurant());
        etDescription.setText(dish.getDescription());
    }

    private void updateDish() {
        String dishName = etDishName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String dishType = etDishType.getText().toString().trim();
        String restaurant = etRestaurant.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Create updated dish object
        Dish dish = new Dish(dishName, quantity, price, dishType, restaurant, description);
        dish.setDishId(dishId);

        dishController.updateDish(dish, new DishRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditDishActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditDishActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDish() {
        dishController.deleteDish(dishId, new DishRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditDishActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditDishActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
