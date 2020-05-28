package patterns.backend.graphql.query;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.ProductService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductQueryTest {

  @MockBean ProductService productService;

  ProductQuery productQuery;

  @BeforeEach
  public void setup() {
    productQuery = new ProductQuery();
    productQuery.setProductService(productService);
  }

  @Test
  void getProducts() {
    productQuery.getProducts(1);
    verify(productService).findAll(1);
  }

  @Test
  void getProduct() {
    productQuery.getProduct(0L);
    verify(productService).findProductById(0L);
  }
}
