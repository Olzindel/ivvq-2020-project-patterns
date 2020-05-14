package patterns.backend.repositories;

import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  List<User> findAll(Example<User> example);
}
