package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.OrderItemService;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderItemQueryTest {

    @MockBean
    OrderItemService orderItemService;

    OrderItemQuery orderItemQuery;

    @BeforeEach
    public void setup() {
        orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderItemService(orderItemService);
    }

    @Test
    void getOrderItems() {
        orderItemQuery.getOrderItems(1);
        verify(orderItemService).findAll(1);
    }

    @Test
    void getOrderItem() {
        orderItemQuery.getOrderItem(0L);
        verify(orderItemService).findOrderItemById(0L);
    }
}