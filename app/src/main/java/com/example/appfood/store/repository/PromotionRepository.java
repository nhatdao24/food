package com.example.appfood.store.repository;

import com.example.appfood.store.model.Promotion;
import java.util.List;

/**
 * Repository interface for Promotion data operations
 */
public interface PromotionRepository {

    void getAllPromotions(OnDataLoadedCallback<List<Promotion>> callback);
    void getPromotionById(String promotionId, OnDataLoadedCallback<Promotion> callback);
    void createPromotion(Promotion promotion, OnOperationCallback callback);
    void updatePromotion(Promotion promotion, OnOperationCallback callback);
    void deletePromotion(String promotionId, OnOperationCallback callback);
    void getActivePromotions(OnDataLoadedCallback<List<Promotion>> callback);

    interface OnDataLoadedCallback<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }

    interface OnOperationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}

