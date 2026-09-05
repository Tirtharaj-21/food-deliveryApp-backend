package foodDelivery.app.service;

import foodDelivery.app.dto.request.CreateOrderRequest;
import foodDelivery.app.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    Long createOrder(Long userId, CreateOrderRequest request);

    List<OrderResponse> getUserOrders(Long userId);

    OrderResponse getOrderById(Long userId, Long orderId);

    OrderResponse getOrderStatus(Long userId, Long orderId);

    OrderResponse cancelOrder(Long userId, Long orderId);
}
