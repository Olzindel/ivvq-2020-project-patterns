package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.Product;
import patterns.backend.services.ProductService;

@Component
@Getter
@Setter
public class ProductQuery implements GraphQLQueryResolver {
  @Autowired ProductService productService;

  public List<Product> getProducts(final int count) {
    return productService.findAll(count);
  }

  public Product getProduct(final Long productId) {
    return productService.findProductById(productId);
  }
}
