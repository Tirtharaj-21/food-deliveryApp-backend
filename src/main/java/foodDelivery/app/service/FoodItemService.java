package foodDelivery.app.service;

import foodDelivery.app.dto.request.FoodItemRequest;
import foodDelivery.app.dto.response.FoodItemResponse;

import java.util.List;

public interface FoodItemService {
    FoodItemResponse createFoodItem(FoodItemRequest request);

    FoodItemResponse getFoodItemById(Long id);

    List<FoodItemResponse> getFoodItemsByRestaurant(Long restaurantId);

    List<FoodItemResponse> getFoodItemsByCategory(Long categoryId);

//    List<FoodItemResponse> searchFoodItems(String keyword);

    FoodItemResponse updateFoodItem(Long id, FoodItemRequest request);

    void deleteFoodItem(Long id);
}
