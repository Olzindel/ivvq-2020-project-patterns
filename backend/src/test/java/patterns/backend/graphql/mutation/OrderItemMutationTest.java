package patterns.backend.graphql.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.services.OrderItemService;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderItemMutationTest {

    @MockBean
    OrderItemService orderItemService;

    OrderItemMutation orderItemMutation;
    OrderItemInput orderItemInput;

    @BeforeEach
    public void setup() {
        orderItemMutation = new OrderItemMutation();
        orderItemMutation.setOrderItemService(orderItemService);
        orderItemInput = new OrderItemInput();
    }

    @Test
    void createOrderItem() {
        orderItemMutation.createOrderItem(orderItemInput);
        verify(orderItemService).create(orderItemInput);
    }

    @Test
    void deleteOrderItem() {
        orderItemMutation.deleteOrderItem(0L);
        verify(orderItemService).deleteOrderItemById(0L);
    }

    @Test
    void updateOrderItem() {
        orderItemMutation.updateOrderItem(0L, orderItemInput);
        verify(orderItemService).update(0L, orderItemInput);
    }
}