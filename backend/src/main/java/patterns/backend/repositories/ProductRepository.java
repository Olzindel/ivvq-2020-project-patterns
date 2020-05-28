package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {}
