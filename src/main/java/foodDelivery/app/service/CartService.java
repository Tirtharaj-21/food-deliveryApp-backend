package foodDelivery.app.service;

import foodDelivery.app.dto.request.CartItemRequest;
import foodDelivery.app.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    void addItem(Long userId, CartItemRequest request);

    void updateItem(Long userId, Long cartItemId, Integer quantity);

    void removeItem(Long userId, Long cartItemId);

}
