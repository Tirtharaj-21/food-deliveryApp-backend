package foodDelivery.app.controller;

import foodDelivery.app.dto.request.FoodItemRequest;
import foodDelivery.app.dto.response.ApiResponse;
import foodDelivery.app.dto.response.FoodItemResponse;
import foodDelivery.app.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {

    @Autowired
    FoodItemService foodItemService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> createFoodItem(@Valid @RequestBody FoodItemRequest request) {

        foodItemService.createFoodItem(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .data("FoodItem saved")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getFoodItemById/{id}")
    public ResponseEntity<FoodItemResponse> getFoodItemById(@PathVariable Long id) {

        return ResponseEntity.ok(foodItemService.getFoodItemById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItemResponse>> getFoodItemsByRestaurant(@PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemsByRestaurant(restaurantId)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodItemResponse>> getFoodItemsByCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemsByCategory(categoryId)
        );
    }

    @PutMapping("/updateFoodItemById/{id}")
    public ResponseEntity<ApiResponse<String>> updateFoodItem(@PathVariable Long id,
                                                              @Valid @RequestBody FoodItemRequest request) {

        foodItemService.updateFoodItem(id, request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .data("FoodItem modified")
                .build();
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/deleteFoodItem/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFoodItem(@PathVariable Long id) {

        foodItemService.deleteFoodItem(id);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .data("FoodItem deleted")
                .build();
        return ResponseEntity.ok(response);
    }
}
