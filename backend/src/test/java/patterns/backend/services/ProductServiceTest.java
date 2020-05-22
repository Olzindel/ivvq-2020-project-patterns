package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.Product;
import patterns.backend.repositories.ProductRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductServiceTest {

    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ImageLinkService imageLinkService;

    @MockBean
    private Product product;


    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        product = dataLoader.getProduct();

        productService = new ProductService();
        productService.setProductRepository(productRepository);
        productService.setImageLinkService(imageLinkService);
    }

    @Test
    public void testTypeRepository() {
        // the associated Repository of a ProductService is type of CrudRepository
        assertThat(productService.getProductRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findProductById() {
        // given: a Product and a ProductService
        when(productService.getProductRepository().findById(0L)).thenReturn(java.util.Optional.of(product));
        // when: the findAll method is invoked
        productService.findProductById(0L);
        // then: the findAll method of the Repository is invoked
        verify(productService.getProductRepository()).findById(0L);
    }

    @Test
    void saveProduct() {
        // given: a product and a productService
        when(productService.getProductRepository().save(product)).thenReturn(product);
        // when: saveProduct is invoked
        productService.create(product);
        // then: the save method of ProductRepository is invoked
        verify(productService.getProductRepository()).save(product);
    }

    @Test
    void countProduct() {
        // given: a ProductService
        // when: the count method is invoked
        productService.countProduct();
        // then: the count method of the Repository is invoked
        verify(productService.getProductRepository()).count();
    }

    @Test
    void deleteProduct() {
        // given: a Product and aProductService
        when(productService.getProductRepository().findById(0L)).thenReturn(java.util.Optional.of(product));
        // when: the deleteProductById method is invoked
        productService.deleteProductById(0L);
        // then: the delete method of the Repository is invoked
        verify(productService.getProductRepository()).delete(product);
    }

    @Test
    void findAll() {
        // given: a ProductService
        // when: the findAll method is invoked
        productService.findAll(8);
        // then: the findAll method of the Repository is invoked
        verify(productService.getProductRepository()).findAll();
    }
}