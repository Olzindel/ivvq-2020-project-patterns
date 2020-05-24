package patterns.backend.graphql.query;

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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductQueryIntegrationTest {

    @Autowired
    ProductQuery productQuery;

    @Autowired
    ProductMutation productMutation;

    @Autowired
    ImageLinkMutation imageLinkMutation;

    ProductInput productInput;
    ImageLinkInput imageLinkInput;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        productInput = dataLoader.getProductInput();
        imageLinkInput = dataLoader.getImageLinkInput();
    }

    @Test
    void getProducts() {
        ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
        productInput.setImageLinkIds(Arrays.asList(imageLink.getId()));
        Product product = productMutation.createProduct(productInput);

        List<Product> productsQueried = productQuery.getProducts(2);

        for (Product productQueried : productsQueried) {
            assertEquals(product.getPrice(), productQueried.getPrice());
            assertEquals(product.getImageLinks(), productQueried.getImageLinks());
            assertEquals(product.getStatus(), productQueried.getStatus());
            assertEquals(product.getStock(), productQueried.getStock());
            assertEquals(product.getName(), productQueried.getName());
            assertEquals(product.getDescription(), productQueried.getDescription());
        }
    }

    @Test
    void getProduct() {
        ImageLink imageLink = imageLinkMutation.createImageLink(imageLinkInput);
        productInput.setImageLinkIds(Arrays.asList(imageLink.getId()));
        Product product = productMutation.createProduct(productInput);


        Product productQueried = productQuery.getProduct(product.getId());

        assertEquals(product.getPrice(), productQueried.getPrice());
        assertEquals(product.getImageLinks(), productQueried.getImageLinks());
        assertEquals(product.getStatus(), productQueried.getStatus());
        assertEquals(product.getStock(), productQueried.getStock());
        assertEquals(product.getName(), productQueried.getName());
        assertEquals(product.getDescription(), productQueried.getDescription());
    }
}
