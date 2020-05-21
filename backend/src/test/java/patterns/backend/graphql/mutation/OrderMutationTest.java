package patterns.backend.graphql.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.services.OrderService;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderMutationTest {

    @MockBean
    OrderService orderService;

    OrderMutation orderMutation;
    OrderInput orderInput;

    @BeforeEach
    public void setup() {
        orderMutation = new OrderMutation();
        orderMutation.setOrderService(orderService);
        orderInput = new OrderInput();
    }

    @Test
    void createOrder() {
        orderMutation.createOrder(orderInput);
        verify(orderService).create(orderInput);
    }

    @Test
    void deleteOrder() {
        orderMutation.deleteOrder(0L);
        verify(orderService).deleteOrderById(0L);
    }

    @Test
    void updateOrder() {
        orderMutation.updateOrder(0L, orderInput);
        verify(orderService).update(0L, orderInput);
    }
}