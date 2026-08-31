package foodDelivery.app.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long cartId;

    private List<CartItemResponse> items;

    private BigDecimal subtotal;

    private BigDecimal deliveryFee;

    private BigDecimal tax;

    private BigDecimal discount;

    private BigDecimal totalAmount;
}
