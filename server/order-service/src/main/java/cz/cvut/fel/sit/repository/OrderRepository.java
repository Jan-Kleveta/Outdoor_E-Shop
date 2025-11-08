package cz.cvut.fel.sit.repository;

import cz.cvut.fel.sit.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<List<Order>> findAllByUserId(@Param("user_id") Long userId);
    Optional<List<Order>> findAllByEmail(@Param("email") String email);
}
