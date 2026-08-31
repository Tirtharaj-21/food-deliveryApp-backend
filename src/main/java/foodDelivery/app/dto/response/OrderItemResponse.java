package foodDelivery.app.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;

    private Long foodItemId;

    private String foodName;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal itemTotal;
}
