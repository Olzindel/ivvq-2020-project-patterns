package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.User;
import patterns.backend.repositories.ProductRepository;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceTest {

    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;


    @MockBean
    private Product product;


    @BeforeEach
    public void setup() {
        productService = new ProductService();
        productService.setProductRepository(productRepository);
    }

    @Test
    public void testTypeRepository() {
        // the associated Repository of a ProductService is type of CrudRepository
        assertThat(productService.getProductRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findProductById() {
        // given: a Product and a ProductService
        Product product = new Product();
        when(productService.getProductRepository().findById(0L)).thenReturn(java.util.Optional.of(product));
        // when: the findAll method is invoked
        productService.findProductById(0L);
        // then: the findAll method of the Repository is invoked
        verify(productService.getProductRepository()).findById(0L);
    }

    @Test
    void saveProduct() {
        // given: a product and a productService
        User user = new User("Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        Merchant merchant = new Merchant("Waifu market-dess", LocalDate.now(), user);
        Product product = new Product();
        product.setMerchant(merchant);
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
        Product product = new Product();
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