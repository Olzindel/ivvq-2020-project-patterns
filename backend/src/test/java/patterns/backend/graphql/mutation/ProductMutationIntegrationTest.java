package patterns.backend.graphql.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.exception.ImageLinkNotFoundException;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.graphql.input.ProductInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductMutationIntegrationTest {

  @Autowired ProductMutation productMutation;

  @Autowired ImageLinkMutation imageLinkMutation;

  ProductInput productInput;
  ImageLinkInput imageLinkInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    productInput = dataLoader.getProductInput();
    imageLinkInput = dataLoader.getImageLinkInput();
  }

  @Test
  void createProduct() {
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    productInput.setImageLinkIds(Arrays.asList(imageLink.getId()));

    Product product = productMutation.createProduct(productInput);

    assertEquals(productInput.getName(), product.getName());
    assertEquals((float) productInput.getPrice(), (float) product.getPrice());
    assertEquals(productInput.getStock(), product.getStock());
    assertEquals(productInput.getStatus(), product.getStatus());
    assertEquals(productInput.getDescription(), product.getDescription());
    assertEquals(
        product.getId(),
        imageLinkMutation
            .getImageLinkService()
            .findImageLinkById(imageLink.getId())
            .getProduct()
            .getId());
  }

  @Test
  void deleteProduct() {
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    productInput.setImageLinkIds(Arrays.asList(imageLink.getId()));
    Product product = productMutation.createProduct(productInput);
    long count = productMutation.getProductService().countProduct();

    productMutation.deleteProduct(product.getId());

    assertEquals(count - 1, productMutation.getProductService().countProduct());
    assertThrows(
        ImageLinkNotFoundException.class,
        () -> imageLinkMutation.getImageLinkService().findImageLinkById(imageLink.getId()));
  }

  @Test
  void updateProduct() {
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    productInput.setImageLinkIds(Arrays.asList(imageLink.getId()));
    Product product = productMutation.createProduct(productInput);
    long count = imageLinkMutation.getImageLinkService().countImageLink();

    ProductInput productInputUpdate = new ProductInput();

    ImageLinkInput imageLinkInputUpdate = new ImageLinkInput("http://www.t.com", null);
    ImageLink imageLinkUpdate = imageLinkMutation.createImageLink(imageLinkInputUpdate);

    productInputUpdate.setDescription("p");
    productInputUpdate.setName("p");
    productInputUpdate.setPrice(Float.valueOf("1.0"));
    productInputUpdate.setStatus(ProductStatus.NOT_AVAILABLE);
    productInputUpdate.setStock(12);
    List<Long> imageLinkIds = Arrays.asList(imageLinkUpdate.getId());
    productInputUpdate.setImageLinkIds(imageLinkIds);

    Product productUpdated = productMutation.updateProduct(product.getId(), productInputUpdate);

    assertEquals(count, productMutation.getProductService().countProduct());
    assertEquals(ProductStatus.NOT_AVAILABLE, productUpdated.getStatus());
    assertEquals("p", productUpdated.getDescription());
    assertEquals("p", productUpdated.getName());
    assertEquals(12, productUpdated.getStock());
    assertEquals(1.0, productUpdated.getPrice());
    for (ImageLink o : productUpdated.getImageLinks()) {
      assert imageLinkIds.contains(o.getId());
    }
  }
}
