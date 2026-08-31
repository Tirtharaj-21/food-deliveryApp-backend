package foodDelivery.app.repository;

import foodDelivery.app.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
    List<FoodItem> findByRestaurantId(Long restaurantId);

    List<FoodItem> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);

    List<FoodItem> findByCategoryId(Long categoryId);

    List<FoodItem> findByNameContainingIgnoreCase(String name);

    List<FoodItem> findByRestaurantIdAndCategoryId(
            Long restaurantId,
            Long categoryId
    );
}
