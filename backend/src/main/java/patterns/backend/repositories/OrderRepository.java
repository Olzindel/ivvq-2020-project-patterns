package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {}
