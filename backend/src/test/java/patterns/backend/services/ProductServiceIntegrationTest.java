package patterns.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.exception.ProductNotFoundException;
import patterns.backend.graphql.input.ProductInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductServiceIntegrationTest {
  @Autowired private ProductService productService;

  private Product product;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    product = dataLoader.getProduct();
  }

  @Test
  public void testSavedProductHasId() {
    // given: a Product not persisted product
    // then: product has no id
    assertNull(product.getId());
    // when: product is persisted
    productService.create(product);
    // then: product has a id
    assertNotNull(product.getId());
  }

  @Test()
  public void testSaveProductNull() {
    // when: null is persisted via a ProductService
    // then: a exception IllegalArgumentException is lifted
    assertThrows(IllegalArgumentException.class, () -> productService.create((Product) null));
  }

  @Test
  public void testFetchedProductIsNotNull() {
    // given: a Product product is persisted
    productService.create(product);
    // when: we call findProductById with the id of that Product
    Product fetched = productService.findProductById(product.getId());
    // then: the result is not null
    assertNotNull(fetched);
  }

  @Test
  public void testFetchedProductHasGoodId() {
    // given: a Product product is persisted
    productService.create(product);
    // when: we call findProductById with the id of that Product
    Product fetched = productService.findProductById(product.getId());
    // then: the Product obtained has the correct id
    assertEquals(product.getId(), fetched.getId());
  }

  @Test
  public void testFetchedProductIsUnchanged() {
    // given: a Product product persisted
    productService.create(product);
    // when: we call findProductById with the id of that Product
    Product fetched = productService.findProductById(product.getId());
    // then: All the attributes of the Product obtained has the correct values
    assertEquals(product.getImageLinks(), fetched.getImageLinks());
    assertEquals(product.getName(), fetched.getName());
    assertEquals(product.getPrice(), fetched.getPrice());
    assertEquals(product.getStatus(), fetched.getStatus());
  }

  @Test
  public void testUpdatedProductIsUpdated() {
    // given: a Product product persisted
    Product fetched = productService.create(product);

    ProductInput productInput =
        new ProductInput(
            product.getName(),
            Float.valueOf("0.8"),
            product.getStatus(),
            product.getDescription(),
            product.getStock(),
            product.getImageLinks().stream().map(ImageLink::getId).collect(Collectors.toList()));

    // when: the object product is updated in the database
    productService.update(fetched.getId(), productInput);
    // when: the object product is re-read in the database
    Product fetchedUpdated = productService.findProductById(product.getId());
    // then: the email has been successfully updated
    assertEquals(fetched.getPrice(), fetchedUpdated.getPrice());
  }

  @Test
  public void testSavedProductIsSaved() {
    long before = productService.countProduct();
    // given: is new product
    // when: this USer is persisted
    productService.create(product);
    // then : the number of Product persisted is increased by 1
    assertEquals(before + 1, productService.countProduct());
  }

  @Test
  public void testUpdateDoesNotCreateANewEntry() {
    // given: a Product product persisted
    Product fetched = productService.create(product);
    long count = productService.countProduct();

    ProductInput productInput =
        new ProductInput(
            product.getName(),
            Float.valueOf("0.8"),
            product.getStatus(),
            product.getDescription(),
            product.getStock(),
            product.getImageLinks().stream().map(ImageLink::getId).collect(Collectors.toList()));
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
    productService.create(product);
    Product fetched = productService.findProductById(product.getId());

    // when: deleteProductById is called with an id corresponding to an object in database
    productService.deleteProductById(fetched.getId());
    // then: the product is delete
    assertThrows(
        ProductNotFoundException.class, () -> productService.findProductById(fetched.getId()));
  }

  @Test
  public void testDeleteProductWithUnexistingId() {
    // when: deleteProductById is called with an id not corresponding to any object in database
    // then: an exception is thrown
    assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(0L));
  }
}
