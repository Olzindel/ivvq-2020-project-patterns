package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.Merchant;
import patterns.backend.services.MerchantService;

import java.util.List;

@Component
@Getter
@Setter
public class MerchantQuery implements GraphQLQueryResolver {
    @Autowired
    MerchantService merchantService;

    public List<Merchant> getMerchants(final int count) {
        return merchantService.findAll(count);
    }

    public Merchant getMerchant(final Long merchantId) {
        return merchantService.findMerchantById(merchantId);
    }
}
