package com.example.appfood.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.model.Dish;
import com.example.appfood.view.EditDishActivity;

import java.util.ArrayList;

/**
 * Adapter for displaying dishes in RecyclerView
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private Context context;
    private ArrayList<Dish> dishList;

    public DishAdapter(Context context, ArrayList<Dish> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.tvDishName.setText(dish.getDishName());
        holder.tvDishQuantity.setText("Số lượng: " + dish.getQuantity());
        holder.tvDishPrice.setText(dish.getPrice() + " VND");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditDishActivity.class);
            intent.putExtra("dishId", dish.getDishId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDishImage;
        TextView tvDishName;
        TextView tvDishQuantity;
        TextView tvDishPrice;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDishImage = itemView.findViewById(R.id.ivDishImage);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvDishQuantity = itemView.findViewById(R.id.tvDishQuantity);
            tvDishPrice = itemView.findViewById(R.id.tvDishPrice);
        }
    }
}

