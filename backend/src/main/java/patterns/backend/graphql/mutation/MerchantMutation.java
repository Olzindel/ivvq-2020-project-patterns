package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.User;
import patterns.backend.services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class MerchantMutation implements GraphQLMutationResolver {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @Autowired
    ProductService productService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    UserService userService;

    public Merchant createMerchant(String name, Long userId, List<Long> productIds) {
        Merchant merchant = new Merchant(name, null, null);
        return merchantService.create(merchant, userId, productIds);
    }

    public Long deleteMerchant(Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
        return merchantId;
    }

    public Merchant updateMerchant(Long merchantId, String name, Long userId, String createdAt, List<Long> productIds) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;

        Merchant merchant = merchantService.findMerchantById(merchantId);
        if (productIds != null && !productIds.isEmpty()) {
            List<Long> toDelete = merchant.getProducts().stream().
                    map(Product::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(productIds);

            for (Long idToAdd : toDelete) {
                productService.deleteProductById(idToAdd);
            }
        }

        if (userId != null) {
            merchant.getAdmin().getMerchants().remove(merchant);
            User user = userService.findUserById(userId);
            merchant.setAdmin(user);
        }

        if (name != null) {
            merchant.setName(name);
        }

        if (localcreatedAt != null) {
            merchant.setCreatedAt(localcreatedAt);
        }

        return merchantService.update(merchant);
    }

}
