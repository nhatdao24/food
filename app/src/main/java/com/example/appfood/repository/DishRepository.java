package com.example.appfood.repository;

import com.example.appfood.model.Dish;
import java.util.List;

public interface DishRepository {

    void getAllDishes(OnDataLoadedCallback<List<Dish>> callback);

    void getDishById(String dishId, OnDataLoadedCallback<Dish> callback);

    void createDish(Dish dish, OnOperationCallback callback);

    void updateDish(Dish dish, OnOperationCallback callback);

    void deleteDish(String dishId, OnOperationCallback callback);

    void getDishesByShop(String shopId, OnDataLoadedCallback<List<Dish>> callback);

    interface OnDataLoadedCallback<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }

    interface OnOperationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
