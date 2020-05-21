package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Product;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.services.ProductService;

@Component
@Transactional
@Getter
@Setter
public class ProductMutation implements GraphQLMutationResolver {

    @Autowired
    ProductService productService;

    public Product createProduct(ProductInput productInput) {
        return productService.create(productInput);
    }

    public Long deleteProduct(Long productId) {
        productService.deleteProductById(productId);
        return productId;
    }

    public Product updateProduct(long productId, ProductInput productInput) {
        return productService.update(productId, productInput);
    }

}
