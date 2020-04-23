package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Orders;
import patterns.backend.domain.User;
import patterns.backend.exception.OrdersNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrdersServiceIntegrationTest {

    @Autowired
    private OrdersService ordersService;

    private Orders orders;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("Nathan", "nathan.roche31@gmail.com", "M", LocalDate.now(), LocalDate.now());
        orders = new Orders("Ready", LocalDate.now(), user);
    }

    @Test
    public void testSavedOrdersHasId() {
        // given: an Orders not persisted orders
        // then: orders has no id
        assertNull(orders.getId());
        // when: orders is persisted
        ordersService.saveOrders(orders);
        // then: orders has an id
        assertNotNull(orders.getId());
    }

    @Test()
    public void testSaveOrdersNull() {
        // when: null is persisted via an OrdersService
        // then: an exception IllegalArgumentException is lifted
        assertThrows(IllegalArgumentException.class, () -> ordersService.saveOrders(null));
    }

    @Test
    public void testFetchedOrdersIsNotNull() {
        // given: an Orders orders is persisted
        ordersService.saveOrders(orders);
        // when: we call findOrdersById with the id of that Orders
        Orders fetched = ordersService.findOrdersById(orders.getId());
        // then: the result is not null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedOrdersHasGoodId() {
        // given: an Orders orders is persisted
        ordersService.saveOrders(orders);
        // when: we call findOrdersById with the id of that Orders
        Orders fetched = ordersService.findOrdersById(orders.getId());
        // then: the Orders obtained has the correct id
        assertEquals(orders.getId(), fetched.getId());
    }

    @Test
    public void testFetchedOrdersIsUnchanged() {
        // given: an Orders orders persisted
        ordersService.saveOrders(orders);
        // when: we call findOrdersById with the id of that Orders
        Orders fetched = ordersService.findOrdersById(orders.getId());
        // then: All the attributes of the Orders obtained has the correct values
        assertEquals(orders.getCreatedAt(), fetched.getCreatedAt());
        assertEquals(orders.getStatus(), fetched.getStatus());
    }

    @Test
    public void testUpdatedOrdersIsUpdated() {
        // given: an Orders orders persisted
        ordersService.saveOrders(orders);

        Orders fetched = ordersService.findOrdersById(orders.getId());
        // when: the email is modified at the "object" level
        fetched.setStatus("Aborted");
        // when: the object orders is updated in the database
        ordersService.saveOrders(fetched);
        // when: the object orders is re-read in the database
        Orders fetchedUpdated = ordersService.findOrdersById(orders.getId());
        // then: the email has been successfully updated
        assertEquals(fetched.getStatus(), fetchedUpdated.getStatus());
    }

    @Test
    public void testSavedOrdersIsSaved() {
        long before = ordersService.countOrders();
        // given: is new orders
        // when: this USer is persisted
        ordersService.saveOrders(new Orders("Ready", LocalDate.now(), user));
        // then : the number of Orders persisted is increased by 1
        assertEquals(before + 1, ordersService.countOrders());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        // given: an Orders orders persisted
        ordersService.saveOrders(orders);
        long count = ordersService.countOrders();

        Orders fetched = ordersService.findOrdersById(orders.getId());
        // when: the email is modified at the "object" level
        fetched.setStatus("Aborted");
        // when: the object is updated in the database
        ordersService.saveOrders(fetched);
        // then: a new entry has not been created in the database
        assertEquals(count, ordersService.countOrders());
    }

    @Test
    public void testFindOrdersWithUnexistingId() {
        // when:  findOrdersById is called with an id not corresponding to any object in database
        // then: OrdersNotFoundException is thrown
        assertThrows(OrdersNotFoundException.class, () -> ordersService.findOrdersById(1000L));
    }

    @Test
    public void testDeleteOrdersWithExistingId() {
        // given: an User Orders persisted
        ordersService.saveOrders(orders);
        Orders fetched = ordersService.findOrdersById(orders.getId());

        // when: deleteUserById is called with an id corresponding to an object in database
        ordersService.deleteOrdersById(fetched.getId());
        // then: the orders is delete
        assertThrows(OrdersNotFoundException.class, () -> ordersService.findOrdersById(fetched.getId()));
    }

    @Test
    public void testDeleteOerdersWithUnexistingId() {
        // when: deleteUserById is called with an id not corresponding to any object in database
        // then: an exception is thrown
        assertThrows(OrdersNotFoundException.class, () -> ordersService.deleteOrdersById(0L));
    }
}
