package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.OrderItem;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.services.OrderItemService;

@Component
@Transactional
@Getter
@Setter
public class OrderItemMutation implements GraphQLMutationResolver {

  @Autowired private OrderItemService orderItemService;

  public OrderItem createOrderItem(OrderItemInput orderItemInput) {
    return orderItemService.create(orderItemInput);
  }

  public Long deleteOrderItem(Long orderItemId) {
    orderItemService.deleteOrderItemById(orderItemId);
    return orderItemId;
  }

  public OrderItem updateOrderItem(Long orderItemId, OrderItemInput orderItemInput) {
    return orderItemService.update(orderItemId, orderItemInput);
  }
}
