package patterns.backend.graphql.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.User;
import patterns.backend.exception.OrderItemNotFoundException;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.graphql.input.UserInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderMutationIntegrationTest {

  @Autowired OrderMutation orderMutation;

  @Autowired OrderItemMutation orderItemMutation;

  @Autowired UserMutation userMutation;

  OrderInput orderInput;
  OrderItemInput orderItemInput;
  UserInput userInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    orderInput = dataLoader.getOrderInput();
    userInput = dataLoader.getUserInput();
    orderItemInput = dataLoader.getOrderItemInput();
  }

  @Test
  void createOrder() {
    User user = userMutation.createUser(userInput);
    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    orderInput.setOrderItemIds(Arrays.asList(orderItem.getId()));
    orderInput.setUserId(user.getId());
    Order order = orderMutation.createOrder(orderInput);
    assertEquals(orderInput.getStatus(), order.getStatus());
    assertEquals(
        order.getId(),
        orderItemMutation
            .getOrderItemService()
            .findOrderItemById(orderItem.getId())
            .getOrder()
            .getId());
    assert userMutation.getUserService().findUserById(user.getId()).getOrders().contains(order);
  }

  @Test
  void deleteOrder() {
    User user = userMutation.createUser(userInput);
    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    orderInput.setOrderItemIds(Arrays.asList(orderItem.getId()));
    orderInput.setUserId(user.getId());
    Order order = orderMutation.createOrder(orderInput);
    long count = orderItemMutation.getOrderItemService().countOrderItem();

    orderMutation.deleteOrder(order.getId());

    assertEquals(count - 1, orderMutation.getOrderService().countOrders());
    assertThrows(
        OrderItemNotFoundException.class,
        () -> orderItemMutation.getOrderItemService().findOrderItemById(orderItem.getId()));
    assert !userMutation.getUserService().findUserById(user.getId()).getOrders().contains(order);
  }

  @Test
  void updateOrder() {
    User user = userMutation.createUser(userInput);
    OrderItem orderItem = orderItemMutation.createOrderItem(orderItemInput);
    orderInput.setOrderItemIds(Arrays.asList(orderItem.getId()));
    orderInput.setUserId(user.getId());
    Order order = orderMutation.createOrder(orderInput);
    long count = orderItemMutation.getOrderItemService().countOrderItem();

    OrderInput orderInputUpdate = new OrderInput();
    UserInput userInputUpdate =
        new UserInput("t", "t", "t.t@g.c", "F", "F", "31450", "c", null, null);
    User userUpdate = userMutation.createUser(userInputUpdate);

    OrderItemInput orderItemInputUpdate = new OrderItemInput(4, null, null);
    OrderItem orderItemUpdate = orderItemMutation.createOrderItem(orderItemInput);

    orderInputUpdate.setStatus(OrderStatus.ABORTED);
    orderInputUpdate.setUserId(userUpdate.getId());
    List<Long> orderItemIds = Arrays.asList(orderItemUpdate.getId());
    orderInputUpdate.setOrderItemIds(orderItemIds);

    Order orderUpdated = orderMutation.updateOrder(order.getId(), orderInputUpdate);

    assertEquals(count, orderMutation.getOrderService().countOrders());
    assertEquals(OrderStatus.ABORTED, orderUpdated.getStatus());
    assertEquals(userUpdate.getId(), orderUpdated.getUser().getId());
    for (OrderItem o : orderUpdated.getOrderItems()) {
      assert orderItemIds.contains(o.getId());
    }
    assert userUpdate.getOrders().contains(orderUpdated);
    assert !user.getOrders().contains(orderUpdated);
  }
}
