package patterns.backend.graphql.query;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.MerchantService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MerchantQueryTest {

  @MockBean MerchantService merchantService;

  MerchantQuery merchantQuery;

  @BeforeEach
  public void setup() {
    merchantQuery = new MerchantQuery();
    merchantQuery.setMerchantService(merchantService);
  }

  @Test
  void getMerchants() {
    merchantQuery.getMerchants(1);
    verify(merchantService).findAll(1);
  }

  @Test
  void getMerchant() {
    merchantQuery.getMerchant(0L);
    verify(merchantService).findMerchantById(0L);
  }
}
