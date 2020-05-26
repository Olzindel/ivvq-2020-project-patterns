package patterns.backend.graphql.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.graphql.mutation.ImageLinkMutation;
import patterns.backend.graphql.mutation.ProductMutation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ImageLinkQueryIntegrationTest {

  @Autowired ImageLinkQuery imageLinkQuery;

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
  void getImageLinks() {
    Product product = productMutation.createProduct(productInput);
    imageLinkInput.setProductId(product.getId());
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);

    List<ImageLink> imageLinksQueried = imageLinkQuery.getImageLinks(2);

    for (ImageLink imageLinkQueried : imageLinksQueried) {
      assertEquals(imageLink.getImageLink(), imageLinkQueried.getImageLink());
      assertEquals(imageLink.getProduct(), imageLinkQueried.getProduct());
    }
  }

  @Test
  void getImageLink() {
    Product product = productMutation.createProduct(productInput);
    imageLinkInput.setProductId(product.getId());
    ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);

    ImageLink imageLinkQueried = imageLinkQuery.getImageLink(imageLink.getId());

    assertEquals(imageLink.getImageLink(), imageLinkQueried.getImageLink());
    assertEquals(imageLink.getProduct(), imageLinkQueried.getProduct());
  }
}
