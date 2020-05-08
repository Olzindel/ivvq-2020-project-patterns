package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.Merchant;
import patterns.backend.exception.MerchantNotFoundException;
import patterns.backend.repositories.MerchantRepository;

import java.time.LocalDate;
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

    public Merchant findMerchantById(final Long id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (!optionalMerchant.isPresent()) {
            throw new MerchantNotFoundException(id);
        } else {
            return optionalMerchant.get();
        }
    }

    public Merchant create(final Merchant merchant) {
        Merchant savedMerchant;
        if (merchant != null) {
            merchant.setCreatedAt(LocalDate.now());
            savedMerchant = merchantRepository.save(merchant);
        } else {
            throw new IllegalArgumentException();
        }
        return savedMerchant;
    }

    public Merchant update(final Merchant merchant) {
        Merchant savedMerchant;
        if (merchant != null) {
            savedMerchant = merchantRepository.save(merchant);
        } else {
            throw new IllegalArgumentException();
        }
        return savedMerchant;
    }

    public void deleteMerchantById(final Long id) {
        Merchant merchant = findMerchantById(id);
        merchantRepository.delete(merchant);
    }

    public long countMerchant() {
        return merchantRepository.count();
    }

    public Merchant getMerchantByUser(Long adminId) {
        return merchantRepository.findMerchantByAdmin_Id(adminId);
    }

    public List<Merchant> findAll(int count) {
        return StreamSupport.stream(merchantRepository.findAll().spliterator(), false)
                .limit(count)
                .collect(Collectors.toList());
    }

}
