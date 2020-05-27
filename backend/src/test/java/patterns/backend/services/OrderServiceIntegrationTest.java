package patterns.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.OrderStatus;
import patterns.backend.exception.OrderNotFoundException;
import patterns.backend.graphql.input.OrderInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderServiceIntegrationTest {
  @Autowired private OrderService orderService;

  private Order order;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    order = dataLoader.getOrder();
  }

  @Test
  public void testSavedOrdersHasId() {
    // given: an Orders not persisted orders
    // then: orders has no id
    assertNull(order.getId());
    // when: orders is persisted
    orderService.create(order);
    // then: orders has an id
    assertNotNull(order.getId());
  }

  @Test()
  public void testSaveOrdersNull() {
    // when: null is persisted via an OrdersService
    // then: an exception IllegalArgumentException is lifted
    assertThrows(IllegalArgumentException.class, () -> orderService.create((Order) null));
  }

  @Test
  public void testFetchedOrdersIsNotNull() {
    // given: an Orders orders is persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrderById(order.getId());
    // then: the result is not null
    assertNotNull(fetched);
  }

  @Test
  public void testFetchedOrdersHasGoodId() {
    // given: an Orders orders is persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrderById(order.getId());
    // then: the Orders obtained has the correct id
    assertEquals(order.getId(), fetched.getId());
  }

  @Test
  public void testFetchedOrdersIsUnchanged() {
    // given: an Orders orders persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrderById(order.getId());
    // then: All the attributes of the Orders obtained has the correct values
    assertEquals(order.getStatus(), fetched.getStatus());
  }

  @Test
  public void testUpdatedOrdersIsUpdated() {
    // given: an Orders orders persisted
    Order fetched = orderService.create(order);
    // when: the email is modified at the "object" level
    OrderInput orderInput =
        new OrderInput(
            OrderStatus.ABORTED,
            order.getOrderItems().stream().map(OrderItem::getId).collect(Collectors.toList()),
            order.getUser().getId());
    // when: the object orders is updated in the database
    orderService.update(order.getId(), orderInput);
    // when: the object orders is re-read in the database
    Order fetchedUpdated = orderService.findOrderById(order.getId());
    // then: the email has been successfully updated
    assertEquals(fetched.getStatus(), fetchedUpdated.getStatus());
  }

  @Test
  public void testSavedOrdersIsSaved() {
    long before = orderService.countOrders();
    // given: is new orders
    // when: this USer is persisted
    orderService.create(order);
    // then : the number of Orders persisted is increased by 1
    assertEquals(before + 1, orderService.countOrders());
  }

  @Test
  public void testUpdateDoesNotCreateANewEntry() {
    // given: an Orders orders persisted
    Order fetched = orderService.create(order);
    long count = orderService.countOrders();
    // when: the email is modified at the "object" level
    OrderInput orderInput =
        new OrderInput(
            OrderStatus.ABORTED,
            order.getOrderItems().stream().map(OrderItem::getId).collect(Collectors.toList()),
            order.getUser().getId());

    // when: the object is updated in the database
    orderService.update(fetched.getId(), orderInput);
    // then: a new entry has not been created in the database
    assertEquals(count, orderService.countOrders());
  }

  @Test
  public void testFindOrdersWithUnexistingId() {
    // when:  findOrdersById is called with an id not corresponding to any object in database
    // then: OrdersNotFoundException is thrown
    assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(1000L));
  }

  @Test
  public void testDeleteOrdersWithExistingId() {
    // given: an User Orders persisted
    orderService.create(order);
    Order fetched = orderService.findOrderById(order.getId());

    // when: deleteUserById is called with an id corresponding to an object in database
    orderService.deleteOrderById(fetched.getId());
    // then: the orders is delete
    assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(fetched.getId()));
  }

  @Test
  public void testDeleteOerdersWithUnexistingId() {
    // when: deleteUserById is called with an id not corresponding to any object in database
    // then: an exception is thrown
    assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(0L));
  }
}
