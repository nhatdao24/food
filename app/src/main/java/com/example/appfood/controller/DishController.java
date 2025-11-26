package com.example.appfood.controller;

import com.example.appfood.model.Dish;
import com.example.appfood.repository.DishRepository;
import com.example.appfood.repository.DishRepositoryImpl;

import java.util.List;

/**
 * Controller class for Dish operations
 */
public class DishController {

    private final DishRepository dishRepository;
    private static DishController instance;

    private DishController() {
        this.dishRepository = DishRepositoryImpl.getInstance();
    }

    public static synchronized DishController getInstance() {
        if (instance == null) {
            instance = new DishController();
        }
        return instance;
    }

    public void getAllDishes(DishRepository.OnDataLoadedCallback<List<Dish>> callback) {
        dishRepository.getAllDishes(callback);
    }

    public void getDishById(String dishId, DishRepository.OnDataLoadedCallback<Dish> callback) {
        if (dishId == null || dishId.trim().isEmpty()) {
            callback.onFailure("Dish ID cannot be empty");
            return;
        }
        dishRepository.getDishById(dishId, callback);
    }

    public void createDish(Dish dish, DishRepository.OnOperationCallback callback) {
        String validationError = validateDish(dish);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }
        dishRepository.createDish(dish, callback);
    }

    public void updateDish(Dish dish, DishRepository.OnOperationCallback callback) {
        String validationError = validateDish(dish);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        if (dish.getDishId() == null || dish.getDishId().trim().isEmpty()) {
            callback.onFailure("Dish ID is required for update");
            return;
        }

        dishRepository.updateDish(dish, callback);
    }

    public void deleteDish(String dishId, DishRepository.OnOperationCallback callback) {
        if (dishId == null || dishId.trim().isEmpty()) {
            callback.onFailure("Dish ID cannot be empty");
            return;
        }
        dishRepository.deleteDish(dishId, callback);
    }

    public void getDishesByShop(String shopId, DishRepository.OnDataLoadedCallback<List<Dish>> callback) {
        if (shopId == null || shopId.trim().isEmpty()) {
            callback.onFailure("Shop ID cannot be empty");
            return;
        }
        dishRepository.getDishesByShop(shopId, callback);
    }

    private String validateDish(Dish dish) {
        if (dish == null) {
            return "Dish data cannot be null";
        }

        if (dish.getDishName() == null || dish.getDishName().trim().isEmpty()) {
            return "Vui lòng nhập tên món ăn";
        }

        if (dish.getQuantity() == null || dish.getQuantity().trim().isEmpty()) {
            return "Vui lòng nhập số lượng";
        }

        if (dish.getPrice() == null || dish.getPrice().trim().isEmpty()) {
            return "Vui lòng nhập giá";
        }

        if (dish.getDishType() == null || dish.getDishType().trim().isEmpty()) {
            return "Vui lòng nhập loại món ăn";
        }

        if (dish.getRestaurant() == null || dish.getRestaurant().trim().isEmpty()) {
            return "Vui lòng nhập thông tin quán";
        }

        return null;
    }
}

