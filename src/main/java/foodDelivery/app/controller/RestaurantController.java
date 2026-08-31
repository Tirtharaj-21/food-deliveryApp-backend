package foodDelivery.app.controller;

import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.RestaurantResponse;
import foodDelivery.app.entity.Restaurant;
import foodDelivery.app.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody RestaurantRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        restaurantService.createRestaurant(request)
                );
    }

//    @GetMapping
//    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
//
//        return ResponseEntity.ok(
//                restaurantService.getAllRestaurants()
//        );
//    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                restaurantService.getRestaurantById(id)
        );
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<RestaurantResponse>> searchRestaurants(
//            @RequestParam String keyword
//    ) {
//
//        return ResponseEntity.ok(
//                restaurantService.searchRestaurants(keyword)
//        );
//    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest request
    ) {

        return ResponseEntity.ok(
                restaurantService.updateRestaurant(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id
    ) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.noContent().build();
    }

}
