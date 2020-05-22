package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.graphql.mutation.MerchantMutation;
import patterns.backend.graphql.mutation.ProductMutation;
import patterns.backend.graphql.mutation.UserMutation;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MerchantQueryIntegrationTest {

    @Autowired
    MerchantQuery merchantQuery;

    @Autowired
    MerchantMutation merchantMutation;

    @Autowired
    UserMutation userMutation;

    @Autowired
    ProductMutation productMutation;

    MerchantInput merchantInput;
    UserInput userInput;
    ProductInput productInput;


    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        merchantInput = dataLoader.getMerchantInput();
        userInput = dataLoader.getUserInput();
        productInput = dataLoader.getProductInput();
    }

    @Test
    void getMerchants() {
        User user = userMutation.createUser(userInput);
        Product product = productMutation.createProduct(productInput);
        merchantInput.setUserId(user.getId());
        merchantInput.setProductIds(Arrays.asList(product.getId()));
        Merchant merchant = merchantMutation.createMerchant(merchantInput);

        List<Merchant> merchantsQueried = merchantQuery.getMerchants(2);

        for (Merchant merchantQueried : merchantsQueried) {
            assertEquals(merchant.getName(), merchantQueried.getName());
            assertEquals(merchant.getAdmin(), merchantQueried.getAdmin());
            assertEquals(merchant.getProducts(), merchantQueried.getProducts());
        }
    }

    @Test
    void getMerchant() {
        User user = userMutation.createUser(userInput);
        Product product = productMutation.createProduct(productInput);
        merchantInput.setUserId(user.getId());
        merchantInput.setProductIds(Arrays.asList(product.getId()));
        Merchant merchant = merchantMutation.createMerchant(merchantInput);

        Merchant merchantQueried = merchantQuery.getMerchant(merchant.getId());

        assertEquals(merchant.getName(), merchantQueried.getName());
        assertEquals(merchant.getAdmin(), merchantQueried.getAdmin());
        assertEquals(merchant.getProducts(), merchantQueried.getProducts());
    }
}
