package patterns.backend.graphql.mutation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.services.MerchantService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MerchantMutationTest {

  @MockBean MerchantService merchantService;

  MerchantMutation merchantMutation;
  MerchantInput merchantInput;

  @BeforeEach
  public void setup() {
    merchantMutation = new MerchantMutation();
    merchantMutation.setMerchantService(merchantService);
    merchantInput = new MerchantInput();
  }

  @Test
  void createMerchant() {
    merchantMutation.createMerchant(merchantInput);
    verify(merchantService).create(merchantInput);
  }

  @Test
  void deleteMerchant() {
    merchantMutation.deleteMerchant(0L);
    verify(merchantService).deleteMerchantById(0L);
  }

  @Test
  void updateMerchant() {
    merchantMutation.updateMerchant(0L, merchantInput);
    verify(merchantService).update(0L, merchantInput);
  }
}
