package com.example.giaodien;

public class Promotion {
    private String promotionId = "";
    private String voucherName = "";
    private String discountValue = "";
    private String condition = "";
    private String endDate = "";
    private long quantity = 0;
    private int status = 0; // 0: Active, 1: Inactive

    public Promotion() {
        // Default constructor required for Firebase. 
        // All fields are initialized to safe, non-null values.
    }

    // Getters and Setters
    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
