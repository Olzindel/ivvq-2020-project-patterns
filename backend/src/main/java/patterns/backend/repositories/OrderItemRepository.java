package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
