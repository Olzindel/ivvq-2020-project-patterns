package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {}
