package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.Merchant;

import java.util.List;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    List<Merchant> findMerchantByAdmin_Id(Long id);
}
