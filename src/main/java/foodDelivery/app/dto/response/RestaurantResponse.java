package foodDelivery.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private String phone;

    private String address;

    private String city;

    private Double rating;

    private Boolean isActive;
}
