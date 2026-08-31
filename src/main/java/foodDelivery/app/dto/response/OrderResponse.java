package foodDelivery.app.dto.response;

import foodDelivery.app.enumerator.OrderStatus;
import foodDelivery.app.enumerator.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderResponse {
    private Long id;

    private Long userId;

    private Long addressId;

    private BigDecimal subtotal;

    private BigDecimal deliveryFee;

    private BigDecimal tax;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private OrderStatus status;

    private List<OrderItemResponse> items;

    private LocalDateTime createdAt;
}
