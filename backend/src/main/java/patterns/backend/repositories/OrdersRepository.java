package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
}
