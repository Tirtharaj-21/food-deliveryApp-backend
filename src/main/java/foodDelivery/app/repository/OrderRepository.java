package foodDelivery.app.repository;


import foodDelivery.app.entity.Order;
import foodDelivery.app.enumerator.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}