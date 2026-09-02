package foodDelivery.app.controller;

import foodDelivery.app.dto.request.CartItemRequest;
import foodDelivery.app.dto.response.ApiResponse;
import foodDelivery.app.dto.response.CartResponse;
import foodDelivery.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/save/items/{userId}")
    public ResponseEntity<ApiResponse<String>> addItem (@PathVariable Long userId, @Valid @RequestBody CartItemRequest request) {

        cartService.addItem(userId, request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data("Item added to cart successfully")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("getCartById/{userId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long userId) {

        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/items/{userId}/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> updateItem (@PathVariable Long userId, @PathVariable Long cartItemId,
                                                   @RequestParam Integer quantity) {

        cartService.updateItem(userId, cartItemId, quantity);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data("Item updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> removeItem (@PathVariable Long userId, @PathVariable Long cartItemId) {

        cartService.removeItem(userId, cartItemId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .data("item is removed")
                .build();
        return ResponseEntity.ok(response);
    }

}
