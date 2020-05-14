package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.User;
import patterns.backend.repositories.OrderRepository;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest {

    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private Order order;


    private User user;
    private Merchant merchant;

    @BeforeEach
    public void setup() {
        orderService = new OrderService();
        orderService.setOrderRepository(orderRepository);

        user = new User("Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        merchant = new Merchant("Market", LocalDate.now(), user);
        order = new Order(LocalDate.now(), OrderStatus.PAID, user);
    }


    @Test
    public void testTypeRepository() {
        // the associated Repository of an OrdersService is type of CrudRepository
        assertThat(orderService.getOrderRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findOrdersById() {
        // given: an Orders and an OrdersService
        when(orderService.getOrderRepository().findById(0L)).thenReturn(java.util.Optional.of(order));
        // when: the findAll method is invoked
        orderService.findOrdersById(0L);
        // then: the findAll method of the Repository is invoked
        verify(orderService.getOrderRepository()).findById(0L);
    }

    @Test
    void saveOrders() {
        // given: an orders and an ordersService
        User user = new User("Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        Order order = new Order();
        order.setUser(user);
        when(orderService.getOrderRepository().save(order)).thenReturn(order);

        // when: saveOrders is invoked
        orderService.create(order);

        // then: the save method of OrdersRepository is invoked
        verify(orderService.getOrderRepository()).save(order);
    }

    @Test
    void countOrders() {
        // given: an OrdersService
        // when: the count method is invoked
        orderService.countOrders();
        // then: the count method of the Repository is invoked
        verify(orderService.getOrderRepository()).count();
    }

    @Test
    void deleteOrders() {
        // given: an Orders and an OrdersService
        when(orderService.getOrderRepository().findById(0L)).thenReturn(java.util.Optional.of(order));
        //when(orderItemService.deleteOrderItemById(any(Long.class))).thenReturn()
        // when: the deleteOrdersById method is invoked
        orderService.deleteOrderById(0L);
        // then: the delete method of the Repository is invoked
        verify(orderService.getOrderRepository()).delete(order);
    }

    @Test
    void findAll() {
        // given: an OrdersService
        // when: the findAll method is invoked
        orderService.findAll(8);
        // then: the findAll method of the Repository is invoked
        verify(orderService.getOrderRepository()).findAll();
    }
}