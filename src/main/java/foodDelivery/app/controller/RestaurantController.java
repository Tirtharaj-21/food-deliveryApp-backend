package foodDelivery.app.controller;

import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.ApiResponse;
import foodDelivery.app.dto.response.RestaurantResponse;
import foodDelivery.app.entity.Restaurant;
import foodDelivery.app.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Builder
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> createRestaurant(@Valid @RequestBody RestaurantRequest request) {

        restaurantService.createRestaurant(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("Restaurant created successfully")
                .data("Restaurant is stored successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAll/restaurants/{pageNo}/{pageSize}")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(@PathVariable int pageNo, @PathVariable int pageSize,
                                                                      @RequestParam (required=false)String search,
                                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                                      @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageNo,pageSize,search,sortBy,sortDir));
    }

    @GetMapping("/getRestaurantById/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {

        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PutMapping("updateRestaurantById/{id}")
    public ResponseEntity<ApiResponse<String>> updateRestaurant(@PathVariable Long id,
                                                               @Valid @RequestBody RestaurantRequest request) {

        restaurantService.updateRestaurant(id, request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("Modify successfully")
                .data("Restaurant is updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteRestaurantById/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRestaurant(@PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .message("Deleted")
                .data("Restaurant is deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
