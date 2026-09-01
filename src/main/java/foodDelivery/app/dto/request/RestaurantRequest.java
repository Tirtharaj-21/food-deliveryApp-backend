package foodDelivery.app.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestaurantRequest {
    @NotBlank(message = "Restaurant name is required")
    @Size(max = 150, message = "Restaurant name cannot exceed 150 characters")
    private String name;

    private String description;

    private String imageUrl;

    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5")
    private Double rating;

    private String cuisine;

    private String deliveryTime;

    private Double deliveryFee;

    private Boolean isActive = true;
}
