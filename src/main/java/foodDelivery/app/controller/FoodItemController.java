package foodDelivery.app.controller;

import foodDelivery.app.dto.request.FoodItemRequest;
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
    @PostMapping
    public ResponseEntity<FoodItemResponse> createFoodItem(
            @Valid @RequestBody FoodItemRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        foodItemService.createFoodItem(request)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponse> getFoodItemById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemById(id)
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItemResponse>>
    getFoodItemsByRestaurant(
            @PathVariable Long restaurantId
    ) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemsByRestaurant(
                        restaurantId
                )
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodItemResponse>>
    getFoodItemsByCategory(
            @PathVariable Long categoryId
    ) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemsByCategory(
                        categoryId
                )
        );
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<FoodItemResponse>>
//    searchFoodItems(
//            @RequestParam String keyword
//    ) {
//
//        return ResponseEntity.ok(
//                foodItemService.searchFoodItems(keyword)
//        );
//    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResponse> updateFoodItem(
            @PathVariable Long id,
            @Valid @RequestBody FoodItemRequest request
    ) {

        return ResponseEntity.ok(
                foodItemService.updateFoodItem(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(
            @PathVariable Long id
    ) {

        foodItemService.deleteFoodItem(id);

        return ResponseEntity.noContent().build();
    }
}
