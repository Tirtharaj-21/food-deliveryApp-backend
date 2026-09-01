package foodDelivery.app.service;

import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    void createRestaurant(RestaurantRequest request);

    RestaurantResponse getRestaurantById(Long id);

    List<RestaurantResponse> getAllRestaurants(int pageNo, int pageSize, String search, String sortBy, String sortDir);


    void updateRestaurant(Long id, RestaurantRequest request);

    void deleteRestaurant(Long id);
}
