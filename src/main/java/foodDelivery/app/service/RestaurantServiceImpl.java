package foodDelivery.app.service;

import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.RestaurantResponse;
import foodDelivery.app.entity.Restaurant;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request
    ) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .rating(request.getRating())
                .isActive(request.getIsActive())
                .build();

        return mapToResponse(
                restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );

        return mapToResponse(restaurant);
    }

    @Override
    public RestaurantResponse updateRestaurant(
            Long id,
            RestaurantRequest request
    ) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setImageUrl(request.getImageUrl());
        restaurant.setPhone(request.getPhone());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setRating(request.getRating());
        restaurant.setIsActive(request.getIsActive());

        return mapToResponse(
                restaurantRepository.save(restaurant)
        );
    }
    @Override
    public void deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );

        // Soft delete
        restaurant.setIsActive(false);

        restaurantRepository.save(restaurant);
    }



    private RestaurantResponse mapToResponse(
            Restaurant restaurant
    ) {

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .imageUrl(restaurant.getImageUrl())
                .phone(restaurant.getPhone())
                .address(restaurant.getAddress())
                .city(restaurant.getCity())
                .rating(restaurant.getRating())
                .isActive(restaurant.getIsActive())
                .build();
    }
}
