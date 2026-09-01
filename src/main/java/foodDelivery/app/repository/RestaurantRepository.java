package foodDelivery.app.repository;

import foodDelivery.app.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByIsActiveTrue();

    List<Restaurant> findByCityIgnoreCase(String city);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

}
