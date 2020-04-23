package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.Merchant;
import patterns.backend.exception.MerchantNotFoundException;
import patterns.backend.repositories.MerchantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Merchant findMerchantById(Long id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (!optionalMerchant.isPresent()) {
            throw new MerchantNotFoundException(id);
        } else {
            return optionalMerchant.get();
        }
    }

    public Merchant saveMerchant(Merchant market) {
        Merchant savedMarket;
        if (market != null) {
            savedMarket = merchantRepository.save(market);
        } else {
            throw new IllegalArgumentException();
        }
        return savedMarket;
    }

    public void deleteMerchantById(Long id) {
        Merchant merchant = findMerchantById(id);
        merchantRepository.delete(merchant);
    }

    public long countMerchant() {
        return merchantRepository.count();
    }

    public List<Merchant> findAll() {
        return StreamSupport.stream(merchantRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
