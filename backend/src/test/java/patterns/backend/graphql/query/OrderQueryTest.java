package patterns.backend.graphql.query;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.OrderService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderQueryTest {

  @MockBean OrderService orderService;

  OrderQuery orderQuery;

  @BeforeEach
  public void setup() {
    orderQuery = new OrderQuery();
    orderQuery.setOrderService(orderService);
  }

  @Test
  void getOrders() {
    orderQuery.getOrders(1);
    verify(orderService).findAll(1);
  }

  @Test
  void getOrder() {
    orderQuery.getOrder(0L);
    verify(orderService).findOrderById(0L);
  }
}
