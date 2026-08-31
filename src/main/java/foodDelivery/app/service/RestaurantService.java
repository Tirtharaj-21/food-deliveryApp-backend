package foodDelivery.app.service;

import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest request);

    RestaurantResponse getRestaurantById(Long id);

//    List<RestaurantResponse> getAllRestaurants();

//    List<RestaurantResponse> searchRestaurants(String keyword);

    RestaurantResponse updateRestaurant(Long id, RestaurantRequest request);

    void deleteRestaurant(Long id);
}
