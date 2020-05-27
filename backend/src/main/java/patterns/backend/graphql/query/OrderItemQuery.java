package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.OrderItem;
import patterns.backend.services.OrderItemService;

@Component
@Getter
@Setter
public class OrderItemQuery implements GraphQLQueryResolver {
  @Autowired OrderItemService orderItemService;

  public List<OrderItem> getOrderItems(final int count) {
    return orderItemService.findAll(count);
  }

  public OrderItem getOrderItem(final Long orderItemId) {
    return orderItemService.findOrderItemById(orderItemId);
  }
}
