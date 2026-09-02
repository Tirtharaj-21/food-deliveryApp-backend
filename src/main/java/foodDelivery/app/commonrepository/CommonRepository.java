package foodDelivery.app.commonrepository;

import foodDelivery.app.entity.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CommonRepository {

    @Select("""
        <script>
        SELECT id, name, description, image_url, address, phone, city,
               cuisine, rating, delivery_time, delivery_fee, created_at, is_active
        FROM restaurants
        WHERE is_active = true
        <if test="search != null">
            AND (LOWER(name) LIKE CONCAT('%', LOWER(#{search}), '%')
                OR LOWER(cuisine) LIKE CONCAT('%', LOWER(#{search}), '%')
                OR LOWER(city) LIKE CONCAT('%', LOWER(#{search}), '%'))
        </if>
        ORDER BY ${sortBy} ${direction}
        LIMIT #{pageSize} OFFSET #{offset}
        </script>
        """)
    List<Restaurant> findAll(
            @Param("search") String search,
            @Param("sortBy") String sortBy,
            @Param("direction") String direction,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    @Select("""
        <script>
        SELECT COUNT(*)
        FROM restaurants
        WHERE is_active = true
        <if test="search != null">
            AND (LOWER(name) LIKE CONCAT('%', LOWER(#{search}), '%')
                OR LOWER(cuisine) LIKE CONCAT('%', LOWER(#{search}), '%')
                OR LOWER(city) LIKE CONCAT('%', LOWER(#{search}), '%'))
        </if>
        </script>
        """)
    long countAll(@Param("search") String search);

    @Select("""
    SELECT COALESCE(
        SUM(ci.quantity * fi.price),
        0
    )
    FROM CartItem ci
    JOIN ci.foodItem fi
    WHERE ci.cart.id = :cartId
""")
    BigDecimal calculateSubtotal(@Param("cartId") Long cartId);
}
