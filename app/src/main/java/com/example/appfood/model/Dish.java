package com.example.appfood.model;

/**
 * Model class representing a Dish (Menu Item)
 */
public class Dish {
    private String dishId;
    private String dishName;
    private String quantity;
    private String price;
    private String dishType;
    private String restaurant;
    private String description;

    public Dish() {
        // Default constructor required for calls to DataSnapshot.getValue(Dish.class)
    }

    public Dish(String dishName, String quantity, String price, String dishType, String restaurant, String description) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.price = price;
        this.dishType = dishType;
        this.restaurant = restaurant;
        this.description = description;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
