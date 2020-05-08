package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Merchant;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    Merchant findMerchantByAdmin_Id(Long id);
}
