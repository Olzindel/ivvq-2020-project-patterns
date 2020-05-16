package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.DirtiesContext;
import patterns.backend.domain.*;
import patterns.backend.repositories.OrderItemRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderItemServiceTest {

    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderItem orderItem;

    private Product product;
    private User user;
    private Merchant merchant;
    private Order order;

    @BeforeEach
    public void setup() {
        orderItemService = new OrderItemService();
        orderItemService.setOrderItemRepository(orderItemRepository);
        user = new User("Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        merchant = new Merchant("Market", LocalDate.now(), user);
        product = new Product("Saber", 100000.0, ProductStatus.AVAILABLE, "Description", 2, LocalDate.now(), merchant);
        order = new Order(LocalDate.now(), OrderStatus.PAID, user);
        orderItem = new OrderItem(2, product, order);
    }


    @Test
    public void testTypeRepository() {
        // the associated Repository of an OrderItemService is type of CrudRepository
        assertThat(orderItemService.getOrderItemRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findOrderItemById() {
        // given: an OrderItem and  an OrderItemService
        when(orderItemService.getOrderItemRepository().findById(0L)).thenReturn(java.util.Optional.of(orderItem));
        // when: the findAll method is invoked
        orderItemService.findOrderItemById(0L);
        // then: the findAll method of the Repository is invoked
        verify(orderItemService.getOrderItemRepository()).findById(0L);
    }

    @Test
    void saveOrderItem() {
        // given: an orderItem and an orderItemService
        when(orderItemService.getOrderItemRepository().save(orderItem)).thenReturn(orderItem);

        // when: saveOrderItem is invoked
        orderItemService.create(orderItem);

        // then: the save method of OrderItemRepository is invoked
        verify(orderItemService.getOrderItemRepository()).save(orderItem);
    }

    @Test
    void countOrderItem() {
        // given: an OrderItemService
        // when: the count method is invoked
        orderItemService.countOrderItem();
        // then: the count method of the Repository is invoked
        verify(orderItemService.getOrderItemRepository()).count();
    }

    @Test
    void deleteOrderItem() {
        // given: an OrderItem and  an OrderItemService
        when(orderItemService.getOrderItemRepository().findById(0L)).thenReturn(java.util.Optional.of(orderItem));
        // when: the deleteOrderItemById method is invoked
        orderItemService.deleteOrderItemById(0L);
        // then: the delete method of the Repository is invoked
        verify(orderItemService.getOrderItemRepository()).delete(orderItem);
    }

    @Test
    void findAll() {
        // given: an OrderItemService
        // when: the findAll method is invoked
        orderItemService.findAll(8);
        // then: the findAll method of the Repository is invoked
        verify(orderItemService.getOrderItemRepository()).findAll();
    }
}