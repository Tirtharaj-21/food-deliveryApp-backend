package foodDelivery.app.service;

import foodDelivery.app.dto.request.CartItemRequest;
import foodDelivery.app.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    CartResponse addItem(Long userId, CartItemRequest request);

    CartResponse updateItem(Long userId, Long cartItemId, Integer quantity);

    void removeItem(Long userId, Long cartItemId);

    void clearCart(Long userId);
}
