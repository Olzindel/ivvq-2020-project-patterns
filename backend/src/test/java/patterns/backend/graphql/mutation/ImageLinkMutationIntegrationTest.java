package patterns.backend.graphql.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.graphql.input.ProductInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ImageLinkMutationIntegrationTest {

  @Autowired ImageLinkMutation imageLinkMutation;

  @Autowired ProductMutation productMutation;

  ImageLinkInput imageLinkInput;
  ProductInput productInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    imageLinkInput = dataLoader.getImageLinkInput();
    productInput = dataLoader.getProductInput();
  }

  @Test
  public void createImageLink() {
    Product product = productMutation.createProduct(productInput);
    imageLinkInput.setProductId(product.getId());
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    assertEquals(imageLinkInput.getImageLink(), imageLink.getImageLink());
    assert productMutation
        .getProductService()
        .findProductById(product.getId())
        .getImageLinks()
        .contains(imageLink);
  }

  @Test
  public void deleteImageLink() {
    Product product = productMutation.createProduct(productInput);
    imageLinkInput.setProductId(product.getId());
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    long count = imageLinkMutation.getImageLinkService().countImageLink();
    imageLinkMutation.deleteImageLink(imageLink.getId());
    assertEquals(count - 1, imageLinkMutation.getImageLinkService().countImageLink());
    assert !productMutation
        .getProductService()
        .findProductById(product.getId())
        .getImageLinks()
        .contains(imageLink);
  }

  @Test
  public void updateImageLink() {
    Product product = productMutation.createProduct(productInput);
    imageLinkInput.setProductId(product.getId());
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
    long count = imageLinkMutation.getImageLinkService().countImageLink();

    ImageLinkInput imageLinkInputUpdate = new ImageLinkInput();
    ProductInput productInput2 =
        new ProductInput(
            "test", Float.parseFloat("1.0"), ProductStatus.NOT_AVAILABLE, "t", 1, null, null);
    Product product2 = productMutation.createProduct(productInput2);
    imageLinkInputUpdate.setImageLink("http://www.t.t");
    imageLinkInputUpdate.setProductId(product2.getId());
    ImageLink imageLinkUpdated =
        imageLinkMutation.updateImageLink(imageLink.getId(), imageLinkInputUpdate);

    assertEquals(count, imageLinkMutation.getImageLinkService().countImageLink());
    assertEquals("http://www.t.t", imageLinkUpdated.getImageLink());
    assertEquals(product2.getId(), imageLinkUpdated.getProduct().getId());
    assert product2.getImageLinks().contains(imageLinkUpdated);
    assert !product.getImageLinks().contains(imageLinkUpdated);
  }
}
