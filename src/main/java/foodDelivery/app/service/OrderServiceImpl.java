package foodDelivery.app.service;

import foodDelivery.app.dto.request.CreateOrderRequest;
import foodDelivery.app.dto.response.OrderItemResponse;
import foodDelivery.app.dto.response.OrderResponse;
import foodDelivery.app.entity.*;
import foodDelivery.app.enumerator.OrderStatus;
import foodDelivery.app.exception.BadRequestException;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.AddressRepository;
import foodDelivery.app.repository.CartRepository;
import foodDelivery.app.repository.OrderItemRepository;
import foodDelivery.app.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired CartRepository cartRepository;
    @Autowired AddressRepository addressRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {

        // 1. Get cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new BadRequestException("Cart is empty")
                );

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        // 2. Validate address
        Address address = addressRepository.findByIdAndUserId(request.getAddressId(), userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found")
                );

        // 3. Calculate subtotal
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            FoodItem foodItem = cartItem.getFoodItem();

            if (!foodItem.getIsAvailable()) {
                throw new BadRequestException(
                        foodItem.getName()
                                + " is currently unavailable"
                );
            }

            BigDecimal itemTotal =
                    foodItem.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );

            subtotal = subtotal.add(itemTotal);
        }

        // 4. Calculate charges
        BigDecimal deliveryFee = new BigDecimal("40.00");

        BigDecimal tax =
                subtotal.multiply(
                        new BigDecimal("0.05")
                );

        BigDecimal discount = BigDecimal.ZERO;

        BigDecimal total = subtotal
                        .add(deliveryFee)
                        .add(tax)
                        .subtract(discount);

        // 5. Create order
        Order order = Order.builder()
                .user(address.getUser())
                .address(address)
                .subtotal(subtotal)
                .deliveryFee(deliveryFee)
                .tax(tax)
                .discount(discount)
                .totalAmount(total)
                .paymentMethod(request.getPaymentMethod())
                .status(OrderStatus.PLACED)
                .build();

        order = orderRepository.save(order);

        // 6. Create order items
        for (CartItem cartItem : cartItems) {

            FoodItem foodItem = cartItem.getFoodItem();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .foodItem(foodItem)
                    .foodName(foodItem.getName())
                    .price(foodItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItemRepository.save(orderItem);
        }

        // 7. Clear cart
        cartItems.clear();
        cartRepository.save(cart);

        // 8. Return response
        return getOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {

        return orderRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::getOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long userId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"
                        )
                );

        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException(
                    "You are not allowed to access this order"
            );
        }

        return getOrderResponse(order);
    }

    @Override
    public OrderResponse getOrderStatus(Long userId, Long orderId) {

        return getOrderById(userId, orderId);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"
                        )
                );

        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException(
                    "You are not allowed to cancel this order"
            );
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new BadRequestException(
                    "Delivered order cannot be cancelled"
            );
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException(
                    "Order is already cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        return getOrderResponse(
                orderRepository.save(order)
        );
    }

    private OrderResponse getOrderResponse(Order order) {

        List<OrderItemResponse> items =
                orderItemRepository.findByOrderId(order.getId())
                        .stream()
                        .map(this::mapOrderItem)
                        .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .addressId(order.getAddress().getId())
                .subtotal(order.getSubtotal())
                .deliveryFee(order.getDeliveryFee())
                .tax(order.getTax())
                .discount(order.getDiscount())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .items(items)
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderItemResponse mapOrderItem(OrderItem item) {

        BigDecimal itemTotal =
                item.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        );

        return OrderItemResponse.builder()
                .id(item.getId())
                .foodItemId(item.getFoodItem().getId())
                .foodName(item.getFoodName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .itemTotal(itemTotal)
                .build();
    }
}
