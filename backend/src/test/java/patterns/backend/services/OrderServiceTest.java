package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.repositories.OrderRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderServiceTest {

    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private Order order;


    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        order = dataLoader.getOrder();

        orderService = new OrderService();
        orderService.setOrderRepository(orderRepository);
        orderService.setOrderItemService(orderItemService);
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
        orderService.findOrderById(0L);
        // then: the findAll method of the Repository is invoked
        verify(orderService.getOrderRepository()).findById(0L);
    }

    @Test
    void saveOrders() {
        // given: an orders and an ordersService
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