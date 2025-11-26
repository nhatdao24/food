package com.example.appfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private Context context;
    private ArrayList<Promotion> promotionList;

    public PromotionAdapter(Context context, ArrayList<Promotion> promotionList) {
        this.context = context;
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.promotion_item, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = promotionList.get(position);
        if (promotion == null) return;

        holder.tvVoucherName.setText(promotion.getVoucherName() != null ? promotion.getVoucherName() : "");
        holder.tvDiscountValue.setText("Giảm: " + (promotion.getDiscountValue() != null ? promotion.getDiscountValue() : "0") + "VND");

        if (promotion.getStatus() == 0) {
            holder.tvStatus.setText("Trạng thái: Còn hiệu lực");
        } else {
            holder.tvStatus.setText("Trạng thái: Hết hiệu lực");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPromotionActivity.class);
            intent.putExtra("promotionId", promotion.getPromotionId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPromotionImage;
        TextView tvVoucherName;
        TextView tvDiscountValue;
        TextView tvStatus;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPromotionImage = itemView.findViewById(R.id.ivPromotionImage);
            tvVoucherName = itemView.findViewById(R.id.tvVoucherName);
            tvDiscountValue = itemView.findViewById(R.id.tvDiscountValue);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
