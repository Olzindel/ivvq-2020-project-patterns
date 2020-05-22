package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.graphql.mutation.OrderItemMutation;
import patterns.backend.graphql.mutation.OrderMutation;
import patterns.backend.graphql.mutation.UserMutation;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderQueryIntegrationTest {

    @Autowired
    OrderQuery orderQuery;

    @Autowired
    OrderMutation orderMutation;

    @Autowired
    OrderItemMutation orderItemMutation;

    @Autowired
    UserMutation userMutation;

    OrderInput orderInput;
    OrderItemInput orderItemInput;
    UserInput userInput;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        orderInput = dataLoader.getOrderInput();
        orderItemInput = dataLoader.getOrderItemInput();
        userInput = dataLoader.getUserInput();
    }

    @Test
    void getOrders() {
        User user = userMutation.createUser(userInput);
        OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
        orderInput.setOrderItemIds(Arrays.asList(orderItem.getId()));
        orderInput.setUserId(user.getId());
        Order order = orderMutation.createOrder(orderInput);

        List<Order> ordersQueried = orderQuery.getOrders(2);

        for (Order orderQueried : ordersQueried) {
            assertEquals(order.getStatus(), orderQueried.getStatus());
            assertEquals(order.getOrderItems(), orderQueried.getOrderItems());
            assertEquals(order.getUser(), orderQueried.getUser());
        }
    }

    @Test
    void getOrder() {
        User user = userMutation.createUser(userInput);
        OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
        orderInput.setOrderItemIds(Arrays.asList(orderItem.getId()));
        orderInput.setUserId(user.getId());
        Order order = orderMutation.createOrder(orderInput);

        Order orderQueried = orderQuery.getOrder(order.getId());

        assertEquals(order.getStatus(), orderQueried.getStatus());
        assertEquals(order.getOrderItems(), orderQueried.getOrderItems());
        assertEquals(order.getUser(), orderQueried.getUser());
    }
}
