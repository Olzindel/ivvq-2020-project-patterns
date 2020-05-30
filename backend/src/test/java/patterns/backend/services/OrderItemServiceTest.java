package patterns.backend.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.OrderItem;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.repositories.OrderItemRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderItemServiceTest {

  private OrderItemService orderItemService;

  @MockBean private OrderItemRepository orderItemRepository;

  private OrderItem orderItem;
  private OrderItemInput orderItemInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    orderItem = dataLoader.getOrderItem();
    orderItemInput = dataLoader.getOrderItemInput();

    orderItemService = new OrderItemService();
    orderItemService.setOrderItemRepository(orderItemRepository);
  }

  @Test
  public void testTypeRepository() {
    // the associated Repository of an OrderItemService is type of CrudRepository
    assertThat(orderItemService.getOrderItemRepository(), instanceOf(CrudRepository.class));
  }

  @Test
  void findOrderItemById() {
    // given: an OrderItem and  an OrderItemService
    when(orderItemService.getOrderItemRepository().findById(0L))
        .thenReturn(java.util.Optional.of(orderItem));
    // when: the findAll method is invoked
    orderItemService.findOrderItemById(0L);
    // then: the findAll method of the Repository is invoked
    verify(orderItemService.getOrderItemRepository()).findById(0L);
  }

  @Test
  void saveOrderItem() {
    // given: an orderItem and an orderItemService
    when(orderItemService.getOrderItemRepository().save(orderItem)).thenReturn(orderItem);
    int stockBeforeSave = orderItem.getProduct().getStock();
    // when: saveOrderItem is invoked
    orderItemService.create(orderItem);
    // then: the save method of OrderItemRepository is invoked
    verify(orderItemService.getOrderItemRepository()).save(orderItem);
    assert stockBeforeSave - orderItem.getQuantity() == orderItem.getProduct().getStock();
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
    when(orderItemService.getOrderItemRepository().findById(0L))
        .thenReturn(java.util.Optional.of(orderItem));
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

  @Test
  void createOrderItemFromOrderItemInput() {
    // given: an OrderItemInput and an OrderItemService
    OrderItemService orderItemServiceMock = mock(OrderItemService.class);
    doCallRealMethod().when(orderItemServiceMock).create(orderItemInput);
    // when: create(OrderItemInput) method is invoked
    orderItemServiceMock.create(orderItemInput);
    // then: create(OrderItem) method is invoked
    verify(orderItemServiceMock).create(orderItemInput);
  }

  @Test
  void update() {
    // given: an OrderItemInput, an OrderItem and an OrderItemService
    OrderItemService orderItemServiceMock = mock(OrderItemService.class);
    doCallRealMethod().when(orderItemServiceMock).update(0L, orderItemInput);
    when(orderItemServiceMock.findOrderItemById(0L)).thenReturn(orderItem);
    // when: update method is invoked
    orderItemServiceMock.update(0L, orderItemInput);
    // then: create(OrderItem) method is invoked
    verify(orderItemServiceMock).create(orderItem);
  }
}
