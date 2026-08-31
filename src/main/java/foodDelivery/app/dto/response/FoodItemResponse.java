package foodDelivery.app.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemResponse {
    private Long id;

    private Long restaurantId;

    private String restaurantName;

    private Long categoryId;

    private String categoryName;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Boolean isVegetarian;

    private Boolean isAvailable;

}
