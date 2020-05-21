package patterns.backend.graphql.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.services.ProductService;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductMutationTest {

    @MockBean
    ProductService productService;

    ProductMutation productMutation;
    ProductInput productInput;

    @BeforeEach
    public void setup() {
        productMutation = new ProductMutation();
        productMutation.setProductService(productService);
        productInput = new ProductInput();
    }

    @Test
    void createProduct() {
        productMutation.createProduct(productInput);
        verify(productService).create(productInput);
    }

    @Test
    void deleteProduct() {
        productMutation.deleteProduct(0L);
        verify(productService).deleteProductById(0L);
    }

    @Test
    void updateProduct() {
        productMutation.updateProduct(0L, productInput);
        verify(productService).update(0L, productInput);
    }
}