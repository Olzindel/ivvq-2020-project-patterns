package patterns.backend.graphql.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.graphql.input.UserInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MerchantMutationIntegrationTest {

  @Autowired MerchantMutation merchantMutation;

  @Autowired UserMutation userMutation;

  @Autowired ProductMutation productMutation;

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
  void createMerchant() {
    User user = userMutation.createUser(userInput);
    Product product = productMutation.createProduct(productInput);
    merchantInput.setUserId(user.getId());
    merchantInput.setProductIds(Arrays.asList(product.getId()));
    Merchant merchant = merchantMutation.createMerchant(merchantInput);
    assertEquals(merchantInput.getName(), merchant.getName());
    assertEquals(
        merchant.getId(),
        productMutation.getProductService().findProductById(product.getId()).getMerchant().getId());
    assert userMutation
        .getUserService()
        .findUserById(user.getId())
        .getMerchants()
        .contains(merchant);
  }

  @Test
  void deleteMerchant() {
    User user = userMutation.createUser(userInput);
    Product product = productMutation.createProduct(productInput);
    merchantInput.setUserId(user.getId());
    merchantInput.setProductIds(Arrays.asList(product.getId()));
    Merchant merchant = merchantMutation.createMerchant(merchantInput);
    long count = merchantMutation.getMerchantService().countMerchant();

    merchantMutation.deleteMerchant(merchant.getId());

    assertEquals(count - 1, merchantMutation.getMerchantService().countMerchant());
    assert productMutation.getProductService().findProductById(product.getId()).getMerchant()
        == null;
    assert !userMutation
        .getUserService()
        .findUserById(user.getId())
        .getMerchants()
        .contains(merchant);
  }

  @Test
  void updateMerchant() {
    User user = userMutation.createUser(userInput);
    Product product = productMutation.createProduct(productInput);
    merchantInput.setUserId(user.getId());
    merchantInput.setProductIds(Arrays.asList(product.getId()));
    Merchant merchant = merchantMutation.createMerchant(merchantInput);
    long count = merchantMutation.getMerchantService().countMerchant();

    MerchantInput merchantInputUpdate = new MerchantInput();
    UserInput userInputUpdate =
        new UserInput("t", "t", "t.t@g.c", "F", "F", "31450", "c", null, null);
    User userUpdate = userMutation.createUser(userInputUpdate);

    ProductInput productInputUpdate =
        new ProductInput(
            "t", Float.parseFloat("1.0"), ProductStatus.NOT_AVAILABLE, "t", 1, null, null);
    Product productUpdate = productMutation.createProduct(productInputUpdate);

    merchantInputUpdate.setName("http://www.t.t");
    merchantInputUpdate.setUserId(userUpdate.getId());
    List<Long> productIds = Arrays.asList(productUpdate.getId());
    merchantInputUpdate.setProductIds(productIds);

    Merchant merchantUpdated =
        merchantMutation.updateMerchant(merchant.getId(), merchantInputUpdate);

    assertEquals(count, merchantMutation.getMerchantService().countMerchant());
    assertEquals("http://www.t.t", merchantUpdated.getName());
    assertEquals(userUpdate.getId(), merchantUpdated.getAdmin().getId());
    for (Product p : merchantUpdated.getProducts()) {
      assert productIds.contains(p.getId());
    }
    assert userUpdate.getMerchants().contains(merchantUpdated);
    assert !user.getMerchants().contains(merchantUpdated);
  }
}
