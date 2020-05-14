package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.OrderItem;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
