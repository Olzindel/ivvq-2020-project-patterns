package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.services.MerchantService;

@Component
@Transactional
@Getter
@Setter
public class MerchantMutation implements GraphQLMutationResolver {

    @Autowired
    MerchantService merchantService;

    public Merchant createMerchant(MerchantInput merchantInput) {
        return merchantService.create(merchantInput);
    }

    public Long deleteMerchant(Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
        return merchantId;
    }

    public Merchant updateMerchant(Long merchantId, MerchantInput merchantInput) {
        return merchantService.update(merchantId, merchantInput);
    }

}
