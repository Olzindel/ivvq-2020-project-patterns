package patterns.backend.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.*;
import patterns.backend.exception.OrderItemNotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderItemServiceIntegrationTest {
    @Autowired
    private OrderItemService orderItemService;

    private OrderItem orderItem;

    private Order order;

    private Product product;

    private Merchant merchant;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("username","password","Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        merchant = new Merchant("Market", LocalDate.now(), user);
        product = new Product("Saber", 100000.0, ProductStatus.AVAILABLE, "Description", 4, LocalDate.now(), merchant);
        order = new Order(LocalDate.now(), OrderStatus.PAID, user);
        orderItem = new OrderItem(2, product, order);
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
    }

    @Test
    public void testSavedOrderItemHasId() {
        // given: an OrderItem not persisted orderItem
        // then: orderItem has no id
        assertNull(orderItem.getId());
        // when: orderItem is persisted
        orderItemService.create(orderItem);
        // then: orderItem has an id
        assertNotNull(orderItem.getId());
    }

    @Test()
    public void testSaveOrderItemNull() {
        // when: null is persisted via an OrderItemService
        // then: an exception IllegalArgumentException is lifted
        assertThrows(IllegalArgumentException.class, () -> orderItemService.create(null));
    }

    @Test
    public void testFetchedOrderItemIsNotNull() {
        // given: an OrderItem orderItem is persisted
        orderItemService.create(orderItem);
        // when: we call findOrderItemById with the id of that OrderItem
        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());
        // then: the result is not null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedOrderItemHasGoodId() {
        // given: an OrderItem orderItem is persisted
        orderItemService.create(orderItem);
        // when: we call findOrderItemById with the id of that OrderItem
        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());
        // then: the OrderItem obtained has the correct id
        assertEquals(orderItem.getId(), fetched.getId());
    }

    @Test
    public void testFetchedOrderItemIsUnchanged() {
        // given: an OrderItem orderItem persisted
        orderItemService.create(orderItem);
        // when: we call findOrderItemById with the id of that OrderItem
        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());
        // then: All the attributes of the OrderItem obtained has the correct values
        assertEquals(orderItem.getQuantity(), fetched.getQuantity());
    }

    @Test
    public void testUpdatedOrderItemIsUpdated() {
        // given: an OrderItem orderItem persisted
        orderItemService.create(orderItem);

        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());
        // when: the email is modified at the "object" level
        fetched.setQuantity(1);
        // when: the object orderItem is updated in the database
        orderItemService.create(fetched);
        // when: the object orderItem is re-read in the database
        OrderItem fetchedUpdated = orderItemService.findOrderItemById(orderItem.getId());
        // then: the email has been successfully updated
        assertEquals(fetched.getQuantity(), fetchedUpdated.getQuantity());
    }

    @Test
    public void testSavedOrderItemIsSaved() {
        long before = orderItemService.countOrderItem();
        // given: is new orderItem
        // when: this USer is persisted
        orderItemService.create(orderItem);
        // then : the number of OrderItem persisted is increased by 1
        assertEquals(before + 1, orderItemService.countOrderItem());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        // given: an OrderItem orderItem persisted
        orderItemService.create(orderItem);
        long count = orderItemService.countOrderItem();

        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());
        // when: the email is modified at the "object" level
        fetched.setQuantity(1);
        // when: the object is updated in the database
        orderItemService.create(fetched);
        // then: a new entry has not been created in the database
        assertEquals(count, orderItemService.countOrderItem());
    }

    @Test
    public void testFindOrderItemWithUnexistingId() {
        // when:  findOrderItemById is called with an id not corresponding to any object in database
        // then: OrderItemNotFoundException is thrown
        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.findOrderItemById(1000L));
    }

    @Test
    public void testDeleteOrderItemWithExistingId() {
        // given: an User user persisted
        orderItemService.create(orderItem);
        OrderItem fetched = orderItemService.findOrderItemById(orderItem.getId());

        // when: deleteUserById is called with an id corresponding to an object in database
        orderItemService.deleteOrderItemById(fetched.getId());
        // then: the user is delete
        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.findOrderItemById(fetched.getId()));
    }

    @Test
    public void testDeleteOrderItemWithUnexistingId() {
        // when: deleteUserById is called with an id not corresponding to any object in database
        // then: an exception is thrown
        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.deleteOrderItemById(0L));
    }

}
