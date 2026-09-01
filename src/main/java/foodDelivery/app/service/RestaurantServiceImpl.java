package foodDelivery.app.service;

import foodDelivery.app.commonrepository.CommonRepository;
import foodDelivery.app.dto.request.RestaurantRequest;
import foodDelivery.app.dto.response.RestaurantResponse;
import foodDelivery.app.entity.Restaurant;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.RestaurantRepository;
import foodDelivery.app.util.SortColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CommonRepository commonRepository;

    @Override
    public void createRestaurant(RestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .rating(request.getRating())
                .isActive(request.getIsActive())
                .cuisine(request.getCuisine())
                .deliveryFee(request.getDeliveryFee())
                .deliveryTime(request.getDeliveryTime())
                .createdAt(LocalDateTime.now())
                .build();

        restaurantRepository.save(restaurant);
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
    public List<RestaurantResponse> getAllRestaurants(int pageNo, int pageSize, String search, String sortBy, String sortDir){

        final Set<String> ALLOWED_SORT_COLUMNS =
                Set.of("id", "name", "rating", "deliveryTime", "deliveryFee", "createdAt");

        if (!ALLOWED_SORT_COLUMNS.contains(sortBy)) {
            sortBy = "id";
        }
        String sortColumn = SortColumnMapper.toColumnName(sortBy);
        String direction = "desc".equalsIgnoreCase(sortDir) ? "DESC" : "ASC";
        int offset = pageNo * pageSize;
        String searchTerm = (search == null || search.isBlank()) ? null : search.trim();

        List<Restaurant> restaurants = commonRepository.findAll(searchTerm, sortColumn, direction, offset, pageSize);

        System.out.println(restaurants);
        return restaurants.stream()
                .map(this::toResponse)
                .toList();
    }

    private RestaurantResponse toResponse(Restaurant r) {
        return RestaurantResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .description(r.getDescription())
                .imageUrl(r.getImageUrl())
                .phone(r.getPhone())
                .address(r.getAddress())
                .city(r.getCity())
                .rating(r.getRating())
                .cuisine(r.getCuisine())
                .deliveryTime(r.getDeliveryTime())
                .deliveryFee(r.getDeliveryFee())
                .createdAt(r.getCreatedAt())
                .isActive(r.getIsActive())
                .build();
    }


    @Override
    public void updateRestaurant(Long id, RestaurantRequest request) {

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
        restaurant.setCuisine(request.getCuisine());
        restaurant.setDeliveryFee(request.getDeliveryFee());
        restaurant.setDeliveryTime(request.getDeliveryTime());
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurantRepository.save(restaurant);
    }
    @Override
    public void deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found with id: " + id)
                );

        // Soft delete
        restaurant.setIsActive(false);
        restaurantRepository.save(restaurant);
    }



    private RestaurantResponse mapToResponse(Restaurant restaurant) {

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
                .cuisine(restaurant.getCuisine())
                .deliveryFee(restaurant.getDeliveryFee())
                .createdAt(LocalDateTime.now())
                .deliveryTime(restaurant.getDeliveryTime())
                .build();
    }
}
