package patterns.backend.graphql.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.*;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.graphql.input.ProductInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderItemMutationIntegrationTest {

  @Autowired OrderItemMutation orderItemMutation;

  @Autowired OrderMutation orderMutation;

  @Autowired ProductMutation productMutation;

  OrderItemInput orderItemInput;
  ProductInput productInput;
  OrderInput orderInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    orderItemInput = dataLoader.getOrderItemInput();
    productInput = dataLoader.getProductInput();
    orderInput = dataLoader.getOrderInput();
  }

  @Test
  void createOrderItem() {
    Product product = productMutation.createProduct(productInput);
    Order order = orderMutation.createOrder(orderInput);
    orderItemInput.setOrderId(order.getId());
    orderItemInput.setProductId(product.getId());

    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    assertEquals(orderItemInput.getQuantity(), orderItem.getQuantity());
    assert orderMutation
        .getOrderService()
        .findOrderById(order.getId())
        .getOrderItems()
        .contains(orderItem);
  }

  @Test
  void deleteOrderItem() {
    Order order = orderMutation.createOrder(orderInput);
    Product product = productMutation.createProduct(productInput);
    orderItemInput.setOrderId(order.getId());
    orderItemInput.setProductId(product.getId());
    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    long count = orderItemMutation.getOrderItemService().countOrderItem();

    orderItemMutation.deleteOrderItem(orderItem.getId());

    assertEquals(count - 1, orderItemMutation.getOrderItemService().countOrderItem());
    assert !orderMutation
        .getOrderService()
        .findOrderById(order.getId())
        .getOrderItems()
        .contains(orderItem);
  }

  @Test
  void updateOrderItem() {
    Order order = orderMutation.createOrder(orderInput);
    Product product = productMutation.createProduct(productInput);
    orderItemInput.setOrderId(order.getId());
    orderItemInput.setProductId(product.getId());
    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    long count = orderItemMutation.getOrderItemService().countOrderItem();

    OrderItemInput orderItemInputUpdate = new OrderItemInput();
    OrderInput orderInputUpdate = new OrderInput(OrderStatus.ABORTED, null, null);
    Order orderUpdate = orderMutation.createOrder(orderInputUpdate);

    ProductInput productInputUpdate =
        new ProductInput("t", Float.parseFloat("1.0"), ProductStatus.NOT_AVAILABLE, "t", 1, null);
    Product productUpdate = productMutation.createProduct(productInputUpdate);

    orderItemInputUpdate.setQuantity(1);
    orderItemInputUpdate.setOrderId(orderUpdate.getId());
    orderItemInputUpdate.setProductId(productUpdate.getId());

    OrderItem orderItemUpdated =
        orderItemMutation.updateOrderItem(orderItem.getId(), orderItemInputUpdate);

    assertEquals(count, orderItemMutation.getOrderItemService().countOrderItem());
    assertEquals(1, orderItemUpdated.getQuantity());
    assertEquals(orderUpdate.getId(), orderItemUpdated.getOrder().getId());
    assertEquals(productUpdate.getId(), orderItemUpdated.getProduct().getId());
    assert orderUpdate.getOrderItems().contains(orderItemUpdated);
    assert !order.getOrderItems().contains(orderItemUpdated);
  }
}
