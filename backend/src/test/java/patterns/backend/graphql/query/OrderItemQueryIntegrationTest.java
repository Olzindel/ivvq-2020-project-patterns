package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.Product;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.graphql.mutation.OrderItemMutation;
import patterns.backend.graphql.mutation.OrderMutation;
import patterns.backend.graphql.mutation.ProductMutation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderItemQueryIntegrationTest {

    @Autowired
    OrderItemQuery orderItemQuery;

    @Autowired
    OrderItemMutation orderItemMutation;

    @Autowired
    OrderMutation orderMutation;

    @Autowired
    ProductMutation productMutation;

    OrderItemInput orderItemInput;
    OrderInput orderInput;
    ProductInput productInput;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        orderItemInput = dataLoader.getOrderItemInput();
        orderInput = dataLoader.getOrderInput();
        productInput = dataLoader.getProductInput();
    }

    @Test
    void getOrderItems() {
        Product product = productMutation.createProduct(productInput);
        Order order = orderMutation.createOrder(orderInput);
        orderItemInput.setOrderId(order.getId());
        orderItemInput.setProductId(product.getId());
        OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);

        List<OrderItem> orderItemsQueried = orderItemQuery.getOrderItems(2);

        for (OrderItem orderItemQueried : orderItemsQueried) {
            assertEquals(orderItem.getOrder(), orderItemQueried.getOrder());
            assertEquals(orderItem.getQuantity(), orderItemQueried.getQuantity());
            assertEquals(orderItem.getProduct(), orderItemQueried.getProduct());
        }
    }

    @Test
    void getOrderItem() {
        Product product = productMutation.createProduct(productInput);
        Order order = orderMutation.createOrder(orderInput);
        orderItemInput.setOrderId(order.getId());
        orderItemInput.setProductId(product.getId());
        OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);

        OrderItem orderItemQueried = orderItemQuery.getOrderItem(orderItem.getId());

        assertEquals(orderItem.getOrder(), orderItemQueried.getOrder());
        assertEquals(orderItem.getQuantity(), orderItemQueried.getQuantity());
        assertEquals(orderItem.getProduct(), orderItemQueried.getProduct());
    }
}
