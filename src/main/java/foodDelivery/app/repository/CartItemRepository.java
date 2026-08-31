package foodDelivery.app.repository;

import foodDelivery.app.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndFoodItemId(Long cartId, Long foodItemId);

    Optional<CartItem> findByIdAndCartId(Long id, Long cartId);
    List<CartItem> findByCartId(Long cartId);
}