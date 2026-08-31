package foodDelivery.app.dto.request;

import foodDelivery.app.enumerator.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CreateOrderRequest {
    @NotNull(message = "Address ID is required")
    private Long addressId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}