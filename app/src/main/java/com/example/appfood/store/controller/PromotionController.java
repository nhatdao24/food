package com.example.appfood.store.controller;

import com.example.appfood.store.model.Promotion;
import com.example.appfood.store.repository.PromotionRepository;
import com.example.appfood.store.repository.PromotionRepositoryImpl;

import java.util.List;

/**
 * Controller class for Promotion operations
 */
public class PromotionController {

    private final PromotionRepository promotionRepository;
    private static PromotionController instance;

    private PromotionController() {
        this.promotionRepository = PromotionRepositoryImpl.getInstance();
    }

    public static synchronized PromotionController getInstance() {
        if (instance == null) {
            instance = new PromotionController();
        }
        return instance;
    }

    public void getAllPromotions(PromotionRepository.OnDataLoadedCallback<List<Promotion>> callback) {
        promotionRepository.getAllPromotions(callback);
    }

    public void getPromotionById(String promotionId, PromotionRepository.OnDataLoadedCallback<Promotion> callback) {
        if (promotionId == null || promotionId.trim().isEmpty()) {
            callback.onFailure("Promotion ID cannot be empty");
            return;
        }
        promotionRepository.getPromotionById(promotionId, callback);
    }

    public void createPromotion(Promotion promotion, PromotionRepository.OnOperationCallback callback) {
        String validationError = validatePromotion(promotion);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }
        promotionRepository.createPromotion(promotion, callback);
    }

    public void updatePromotion(Promotion promotion, PromotionRepository.OnOperationCallback callback) {
        String validationError = validatePromotion(promotion);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        if (promotion.getPromotionId() == null || promotion.getPromotionId().trim().isEmpty()) {
            callback.onFailure("Promotion ID is required for update");
            return;
        }

        promotionRepository.updatePromotion(promotion, callback);
    }

    public void deletePromotion(String promotionId, PromotionRepository.OnOperationCallback callback) {
        if (promotionId == null || promotionId.trim().isEmpty()) {
            callback.onFailure("Promotion ID cannot be empty");
            return;
        }
        promotionRepository.deletePromotion(promotionId, callback);
    }

    public void getActivePromotions(PromotionRepository.OnDataLoadedCallback<List<Promotion>> callback) {
        promotionRepository.getActivePromotions(callback);
    }

    private String validatePromotion(Promotion promotion) {
        if (promotion == null) {
            return "Promotion data cannot be null";
        }

        if (promotion.getVoucherName() == null || promotion.getVoucherName().trim().isEmpty()) {
            return "Vui lòng nhập tên voucher";
        }

        if (promotion.getDiscountValue() == null || promotion.getDiscountValue().trim().isEmpty()) {
            return "Vui lòng nhập giá trị giảm giá";
        }

        if (promotion.getCondition() == null || promotion.getCondition().trim().isEmpty()) {
            return "Vui lòng nhập điều kiện áp dụng";
        }

        if (promotion.getEndDate() == null || promotion.getEndDate().trim().isEmpty()) {
            return "Vui lòng nhập ngày hết hạn";
        }

        if (promotion.getQuantity() <= 0) {
            return "Số lượng phải lớn hơn 0";
        }

        return null;
    }
}

