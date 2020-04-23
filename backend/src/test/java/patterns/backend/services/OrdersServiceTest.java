package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Orders;
import patterns.backend.repositories.OrdersRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrdersServiceTest {

    private OrdersService ordersService;

    @MockBean
    private OrdersRepository ordersRepository;

    @MockBean
    private Orders orders;

    @BeforeEach
    public void setup() {
        ordersService = new OrdersService();
        ordersService.setOrdersRepository(ordersRepository);
    }


    @Test
    public void testTypeRepository() {
        // the associated Repository of an OrdersService is type of CrudRepository
        assertThat(ordersService.getOrdersRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findOrdersById() {
        // given: an Orders and an OrdersService
        Orders orders = new Orders();
        when(ordersService.getOrdersRepository().findById(0L)).thenReturn(java.util.Optional.of(orders));
        // when: the findAll method is invoked
        ordersService.findOrdersById(0L);
        // then: the findAll method of the Repository is invoked
        verify(ordersService.getOrdersRepository()).findById(0L);
    }

    @Test
    void saveOrders() {
        // given: an orders and an ordersService
        Orders orders = new Orders();
        when(ordersService.getOrdersRepository().save(orders)).thenReturn(orders);

        // when: saveOrders is invoked
        ordersService.saveOrders(orders);

        // then: the save method of OrdersRepository is invoked
        verify(ordersService.getOrdersRepository()).save(orders);
    }

    @Test
    void countOrders() {
        // given: an OrdersService
        // when: the count method is invoked
        ordersService.countOrders();
        // then: the count method of the Repository is invoked
        verify(ordersService.getOrdersRepository()).count();
    }

    @Test
    void deleteOrders() {
        // given: an Orders and an OrdersService
        Orders orders = new Orders();
        when(ordersService.getOrdersRepository().findById(0L)).thenReturn(java.util.Optional.of(orders));
        // when: the deleteOrdersById method is invoked
        ordersService.deleteOrdersById(0L);
        // then: the delete method of the Repository is invoked
        verify(ordersService.getOrdersRepository()).delete(orders);
    }

    @Test
    void findAll() {
        // given: an OrdersService
        // when: the findAll method is invoked
        ordersService.findAll();
        // then: the findAll method of the Repository is invoked
        verify(ordersService.getOrdersRepository()).findAll();
    }
}