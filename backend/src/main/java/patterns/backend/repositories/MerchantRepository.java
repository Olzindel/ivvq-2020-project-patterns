package patterns.backend.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.Merchant;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {

  List<Merchant> findMerchantByAdmin_Id(Long id);
}
