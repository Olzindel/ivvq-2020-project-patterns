package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.services.ImageLinkService;
import patterns.backend.services.MerchantService;
import patterns.backend.services.OrderItemService;
import patterns.backend.services.ProductService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProductMutation implements GraphQLMutationResolver {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ImageLinkService imageLinkService;

    @Autowired
    MerchantService merchantService;


    public Product createProduct(String name, float price, ProductStatus status, String description, int stock, List<Long> imageLinkIds, Long merchantId) {
        Product product = new Product(name, price, status, description, stock, null, null);
        return productService.create(product, imageLinkIds, merchantId);
    }

    public Long deleteProduct(Long productId) {
        productService.deleteProductById(productId);
        return productId;
    }

    public Product updateProduct(long productId, String name, Float price, ProductStatus status, String description, String createdAt, Integer stock, List<Long> imageLinkIds, Long merchantId) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;

        Product product = productService.findProductById(productId);

        if (imageLinkIds != null && !imageLinkIds.isEmpty()) {
            List<Long> toDelete = product.getImageLinks().stream().
                    map(ImageLink::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(imageLinkIds);

            for (Long idToAdd : toDelete) {
                imageLinkService.deleteImageLinkById(idToAdd);
            }
        }
        if (merchantId != null) {
            Merchant merchant = merchantService.findMerchantById(merchantId);
            product.getMerchant().getProducts().remove(product);
            product.setMerchant(merchant);
        }
        if (name != null) {
            product.setName(name);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (status != null) {
            product.setStatus(status);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (stock != null) {
            product.setStock(stock);
        }
        if (localcreatedAt != null) {
            product.setCreatedAt(localcreatedAt);
        }
        return productService.update(product);
    }

}
