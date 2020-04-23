package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.User;
import patterns.backend.exception.ProductNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    private Product product;

    private Merchant merchant;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("Nathan", "nathan@edf.fr", "M", LocalDate.now(), LocalDate.now());
        merchant = new Merchant("Market", LocalDate.now(), user);
        product = new Product("Saber", 100000.0, "Ready", LocalDate.now(), "https://www.google.fr/", merchant);
    }

    @Test
    public void testSavedProductHasId() {
        // given: a Product not persisted product
        // then: product has no id
        assertNull(product.getId());
        // when: product is persisted
        productService.saveProduct(product);
        // then: product has a id
        assertNotNull(product.getId());
    }

    @Test()
    public void testSaveProductNull() {
        // when: null is persisted via a ProductService
        // then: a exception IllegalArgumentException is lifted
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(null));
    }

    @Test
    public void testFetchedProductIsNotNull() {
        // given: a Product product is persisted
        productService.saveProduct(product);
        // when: we call findProductById with the id of that Product
        Product fetched = productService.findProductById(product.getId());
        // then: the result is not null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedProductHasGoodId() {
        // given: a Product product is persisted
        productService.saveProduct(product);
        // when: we call findProductById with the id of that Product
        Product fetched = productService.findProductById(product.getId());
        // then: the Product obtained has the correct id
        assertEquals(product.getId(), fetched.getId());
    }

    @Test
    public void testFetchedProductIsUnchanged() {
        // given: a Product product persisted
        productService.saveProduct(product);
        // when: we call findProductById with the id of that Product
        Product fetched = productService.findProductById(product.getId());
        // then: All the attributes of the Product obtained has the correct values
        assertEquals(product.getCreatedAt(), fetched.getCreatedAt());
        assertEquals(product.getImageLink(), fetched.getImageLink());
        assertEquals(product.getName(), fetched.getName());
        assertEquals(product.getPrice(), fetched.getPrice());
        assertEquals(product.getStatus(), fetched.getStatus());
    }

    @Test
    public void testUpdatedProductIsUpdated() {
        // given: a Product product persisted
        productService.saveProduct(product);

        Product fetched = productService.findProductById(product.getId());
        // when: the image link is modified at the "object" level
        fetched.setImageLink("https://www.google.com/");
        // when: the object product is updated in the database
        productService.saveProduct(fetched);
        // when: the object product is re-read in the database
        Product fetchedUpdated = productService.findProductById(product.getId());
        // then: the email has been successfully updated
        assertEquals(fetched.getImageLink(), fetchedUpdated.getImageLink());
    }

    @Test
    public void testSavedProductIsSaved() {
        long before = productService.countProduct();
        // given: is new product
        // when: this USer is persisted
        productService.saveProduct(new Product("Rikka", 1000000.0, "Ready", LocalDate.now(), "https://www.google.com/", merchant));
        // then : the number of Product persisted is increased by 1
        assertEquals(before + 1, productService.countProduct());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        // given: a Product product persisted
        productService.saveProduct(product);
        long count = productService.countProduct();

        Product fetched = productService.findProductById(product.getId());
        // when: the image link is modified at the "object" level
        fetched.setImageLink("https://www.google.com/");
        // when: the object is updated in the database
        productService.saveProduct(fetched);
        // then: a new entry has not been created in the database
        assertEquals(count, productService.countProduct());
    }

    @Test
    public void testFindProductWithUnexistingId() {
        // when:  findProductById is called with an id not corresponding to any object in database
        // then: ProductNotFoundException is thrown
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(1000L));
    }

    @Test
    public void testDeleteProductWithExistingId() {
        // given: a Product product persisted
        productService.saveProduct(product);
        Product fetched = productService.findProductById(product.getId());

        // when: deleteProductById is called with an id corresponding to an object in database
        productService.deleteProductById(fetched.getId());
        // then: the product is delete
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(fetched.getId()));
    }

    @Test
    public void testDeleteProductWithUnexistingId() {
        // when: deleteProductById is called with an id not corresponding to any object in database
        // then: an exception is thrown
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(0L));
    }

}
