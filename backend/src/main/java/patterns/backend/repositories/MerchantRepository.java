package patterns.backend.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Merchant;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

  List<Merchant> findMerchantByAdmin_Id(Long id);

  void deleteByAdmin_Id(Long id);
}
