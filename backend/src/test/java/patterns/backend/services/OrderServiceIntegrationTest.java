package patterns.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.*;
import patterns.backend.exception.OrdersNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderServiceIntegrationTest {
  @Autowired private OrderService orderService;

  private Order order;

  private Merchant merchant;

  private Product product;

  private OrderItem orderItem;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User("Nathan", "nathan.roche31@gmail.com", "M", LocalDate.now(), LocalDate.now());
    merchant = new Merchant("Market", LocalDate.now(), user);
    product = new Product("Saber", 100000.0, "Ready", "Description", LocalDate.now(), merchant);
    order = new Order(LocalDate.now(), OrderStatus.PAID, user);
    orderItem = new OrderItem(2, product, order);
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    order.setOrderItems(orderItems);
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
    assertThrows(IllegalArgumentException.class, () -> orderService.create(null));
  }

  @Test
  public void testFetchedOrdersIsNotNull() {
    // given: an Orders orders is persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrdersById(order.getId());
    // then: the result is not null
    assertNotNull(fetched);
  }

  @Test
  public void testFetchedOrdersHasGoodId() {
    // given: an Orders orders is persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrdersById(order.getId());
    // then: the Orders obtained has the correct id
    assertEquals(order.getId(), fetched.getId());
  }

  @Test
  public void testFetchedOrdersIsUnchanged() {
    // given: an Orders orders persisted
    orderService.create(order);
    // when: we call findOrdersById with the id of that Orders
    Order fetched = orderService.findOrdersById(order.getId());
    // then: All the attributes of the Orders obtained has the correct values
    assertEquals(order.getCreatedAt(), fetched.getCreatedAt());
    assertEquals(order.getOrderStatus(), fetched.getOrderStatus());
  }

  @Test
  public void testUpdatedOrdersIsUpdated() {
    // given: an Orders orders persisted
    orderService.create(order);

    Order fetched = orderService.findOrdersById(order.getId());
    // when: the email is modified at the "object" level
    fetched.setOrderStatus(OrderStatus.ABORTED);
    // when: the object orders is updated in the database
    orderService.update(fetched);
    // when: the object orders is re-read in the database
    Order fetchedUpdated = orderService.findOrdersById(order.getId());
    // then: the email has been successfully updated
    assertEquals(fetched.getOrderStatus(), fetchedUpdated.getOrderStatus());
  }

  @Test
  public void testSavedOrdersIsSaved() {
    long before = orderService.countOrders();
    // given: is new orders
    // when: this USer is persisted
    orderService.create(new Order(LocalDate.now(), OrderStatus.PAID, user));
    // then : the number of Orders persisted is increased by 1
    assertEquals(before + 1, orderService.countOrders());
  }

  @Test
  public void testUpdateDoesNotCreateANewEntry() {
    // given: an Orders orders persisted
    orderService.create(order);
    long count = orderService.countOrders();

    Order fetched = orderService.findOrdersById(order.getId());
    // when: the email is modified at the "object" level
    fetched.setOrderStatus(OrderStatus.PAID);
    // when: the object is updated in the database
    orderService.update(fetched);
    // then: a new entry has not been created in the database
    assertEquals(count, orderService.countOrders());
  }

  @Test
  public void testFindOrdersWithUnexistingId() {
    // when:  findOrdersById is called with an id not corresponding to any object in database
    // then: OrdersNotFoundException is thrown
    assertThrows(OrdersNotFoundException.class, () -> orderService.findOrdersById(1000L));
  }

  @Test
  public void testDeleteOrdersWithExistingId() {
    // given: an User Orders persisted
    orderService.create(order);
    Order fetched = orderService.findOrdersById(order.getId());

    // when: deleteUserById is called with an id corresponding to an object in database
    orderService.deleteOrderById(fetched.getId());
    // then: the orders is delete
    assertThrows(OrdersNotFoundException.class, () -> orderService.findOrdersById(fetched.getId()));
  }

  @Test
  public void testDeleteOerdersWithUnexistingId() {
    // when: deleteUserById is called with an id not corresponding to any object in database
    // then: an exception is thrown
    assertThrows(OrdersNotFoundException.class, () -> orderService.deleteOrderById(0L));
  }
}
