package foodDelivery.app.service;

import foodDelivery.app.dto.request.CartItemRequest;
import foodDelivery.app.dto.response.CartItemResponse;
import foodDelivery.app.dto.response.CartResponse;
import foodDelivery.app.entity.Cart;
import foodDelivery.app.entity.CartItem;
import foodDelivery.app.entity.FoodItem;
import foodDelivery.app.entity.User;
import foodDelivery.app.exception.BadRequestException;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.CartItemRepository;
import foodDelivery.app.repository.CartRepository;
import foodDelivery.app.repository.FoodItemRepository;
import foodDelivery.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired CartItemRepository cartItemRepository;
    @Autowired FoodItemRepository foodItemRepository;
    @Autowired UserRepository userRepository;

    @Override
    @Transactional
    public CartResponse getCart(Long userId) {

        Cart cart = getOrCreateCart(userId);

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItem(Long userId, CartItemRequest request) {

        Cart cart = getOrCreateCart(userId);

        FoodItem foodItem = foodItemRepository
                .findById(request.getFoodItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Food item not found"
                        )
                );

        if (!foodItem.getIsAvailable()) {
            throw new BadRequestException(
                    "Food item is currently unavailable"
            );
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndFoodItemId(cart.getId(), foodItem.getId())
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .foodItem(foodItem)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateItem(Long userId, Long cartItemId, Integer quantity) {

        Cart cart = getOrCreateCart(userId);

        if (quantity <= 0) {
            throw new BadRequestException(
                    "Quantity must be greater than 0"
            );
        }

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"
                        )
                );

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public void removeItem(Long userId, Long cartItemId) {

        Cart cart = getOrCreateCart(userId);

        CartItem cartItem = cartItemRepository
                .findByIdAndCartId(
                        cartItemId,
                        cart.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"
                        )
                );

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {

        Cart cart = getOrCreateCart(userId);

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    User user = userRepository.findById(userId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "User not found"
                                    )
                            );

                    Cart cart = Cart.builder()
                            .user(user)
                            .items(new ArrayList<>())
                            .build();

                    return cartRepository.save(cart);
                });
    }

    private CartResponse buildCartResponse(Cart cart) {

        var items = cartItemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getCart().getId()
                                .equals(cart.getId())
                )
                .map(this::mapCartItem)
                .toList();

        BigDecimal subtotal = items.stream()
                .map(CartItemResponse::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal deliveryFee = subtotal.compareTo(BigDecimal.ZERO) > 0 ? new BigDecimal("40.00") :
                                                                                            BigDecimal.ZERO;

        BigDecimal tax = subtotal.multiply(new BigDecimal("0.05"));

        BigDecimal discount = BigDecimal.ZERO;

        BigDecimal total = subtotal
                .add(deliveryFee)
                .add(tax)
                .subtract(discount);

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .subtotal(subtotal)
                .deliveryFee(deliveryFee)
                .tax(tax)
                .discount(discount)
                .totalAmount(total)
                .build();
    }

    private CartItemResponse mapCartItem(CartItem item) {

        BigDecimal itemTotal = item.getFoodItem().getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        );

        return CartItemResponse.builder()
                .id(item.getId())
                .foodItemId(
                        item.getFoodItem().getId()
                )
                .foodName(
                        item.getFoodItem().getName()
                )
                .imageUrl(
                        item.getFoodItem().getImageUrl()
                )
                .price(
                        item.getFoodItem().getPrice()
                )
                .quantity(item.getQuantity())
                .itemTotal(itemTotal)
                .build();
    }
}
