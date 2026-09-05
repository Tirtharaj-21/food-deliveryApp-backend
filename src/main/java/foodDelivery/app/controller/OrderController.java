package foodDelivery.app.controller;

import foodDelivery.app.dto.request.CreateOrderRequest;
import foodDelivery.app.dto.response.ApiResponse;
import foodDelivery.app.dto.response.OrderResponse;
import foodDelivery.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/{userId}/save")
    public ResponseEntity<ApiResponse<Long>> createOrder(@PathVariable Long userId,
                                                           @Valid @RequestBody CreateOrderRequest request) {

        Long orderId = orderService.createOrder(userId, request);
        ApiResponse<Long> response = ApiResponse.<Long>builder()
                .success(true)
                .data(orderId)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {

        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/getOrderById/{userId}/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getOrderById(userId, orderId));
    }

    @GetMapping("/status/{userId}/{orderId}")
    public ResponseEntity<OrderResponse> getOrderStatus(@PathVariable Long userId, @PathVariable Long orderId
    ) {

        return ResponseEntity.ok(
                orderService.getOrderStatus(userId, orderId));
    }

    @PutMapping("/cancel/{userId}/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long userId, @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.cancelOrder(userId,orderId));
    }
}
