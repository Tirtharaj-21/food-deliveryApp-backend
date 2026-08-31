package foodDelivery.app.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;

    private Long foodItemId;

    private String foodName;

    private String imageUrl;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal itemTotal;
}
