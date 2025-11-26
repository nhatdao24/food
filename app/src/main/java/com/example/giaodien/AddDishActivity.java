package com.example.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open image picker
                Toast.makeText(AddDishActivity.this, "Chọn hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    saveDish();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (etDishName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etQuantity.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPrice.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etDishType.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên quán ăn", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etRestaurant.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ quán", Toast.LENGTH_SHORT).show();
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

        HashMap<String, Object> dishInfo = new HashMap<>();
        dishInfo.put("dishName", dishName);
        dishInfo.put("quantity", quantity);
        dishInfo.put("price", price);
        dishInfo.put("dishType", dishType);
        dishInfo.put("restaurant", restaurant);
        dishInfo.put("description", description);

        String dishId = mDatabase.child("dishes").push().getKey();

        if (dishId != null) {
            mDatabase.child("dishes").child(dishId).setValue(dishInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddDishActivity.this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDishActivity.this, "Thêm món ăn thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Thêm món ăn thất bại: không thể tạo dish id.", Toast.LENGTH_LONG).show();
        }
    }
}
