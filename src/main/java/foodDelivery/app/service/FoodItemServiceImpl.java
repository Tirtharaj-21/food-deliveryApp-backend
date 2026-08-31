package foodDelivery.app.service;

import foodDelivery.app.dto.request.FoodItemRequest;
import foodDelivery.app.dto.response.FoodItemResponse;
import foodDelivery.app.entity.Category;
import foodDelivery.app.entity.FoodItem;
import foodDelivery.app.entity.Restaurant;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.CategoryRepository;
import foodDelivery.app.repository.FoodItemRepository;
import foodDelivery.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    FoodItemRepository foodItemRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public FoodItemResponse createFoodItem(FoodItemRequest request) {

        Restaurant restaurant = restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found")
                );

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );

        FoodItem foodItem = FoodItem.builder()
                .restaurant(restaurant)
                .category(category)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .isVegetarian(request.getIsVegetarian())
                .isAvailable(request.getIsAvailable())
                .build();

        return mapToResponse(
                foodItemRepository.save(foodItem)
        );
    }

    @Override
    public FoodItemResponse getFoodItemById(Long id) {

        FoodItem foodItem = foodItemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Food item not found with id: " + id
                        )
                );

        return mapToResponse(foodItem);
    }

    @Override
    public List<FoodItemResponse> getFoodItemsByRestaurant(
            Long restaurantId
    ) {

        return foodItemRepository
                .findByRestaurantIdAndIsAvailableTrue(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<FoodItemResponse> getFoodItemsByCategory(
            Long categoryId
    ) {

        return foodItemRepository
                .findByCategoryId(categoryId)
                .stream()
                .filter(FoodItem::getIsAvailable)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public FoodItemResponse updateFoodItem(
            Long id,
            FoodItemRequest request
    ) {

        FoodItem foodItem = foodItemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Food item not found"
                        )
                );

        Restaurant restaurant = restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found"
                        )
                );

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );

        foodItem.setRestaurant(restaurant);
        foodItem.setCategory(category);
        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setIsVegetarian(request.getIsVegetarian());
        foodItem.setIsAvailable(request.getIsAvailable());

        return mapToResponse(
                foodItemRepository.save(foodItem)
        );
    }

    @Override
    public void deleteFoodItem(Long id) {

        FoodItem foodItem = foodItemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Food item not found"
                        )
                );

        foodItem.setIsAvailable(false);

        foodItemRepository.save(foodItem);
    }

    private FoodItemResponse mapToResponse(
            FoodItem foodItem
    ) {

        return FoodItemResponse.builder()
                .id(foodItem.getId())
                .restaurantId(
                        foodItem.getRestaurant().getId()
                )
                .restaurantName(
                        foodItem.getRestaurant().getName()
                )
                .categoryId(
                        foodItem.getCategory().getId()
                )
                .categoryName(
                        foodItem.getCategory().getName()
                )
                .name(foodItem.getName())
                .description(foodItem.getDescription())
                .price(foodItem.getPrice())
                .imageUrl(foodItem.getImageUrl())
                .isVegetarian(foodItem.getIsVegetarian())
                .isAvailable(foodItem.getIsAvailable())
                .build();
    }
}
