package patterns.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.OrderItem;
import patterns.backend.exception.OrderItemNotFoundException;
import patterns.backend.repositories.OrderItemRepository;

@Service
@Getter
@Setter
public class OrderItemService {

  @Autowired private OrderItemRepository orderItemRepository;

  public OrderItem findOrderItemById(final Long id) {
    Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
    if (!optionalOrderItem.isPresent()) {
      throw new OrderItemNotFoundException(id);
    } else {
      return optionalOrderItem.get();
    }
  }

  public OrderItem create(final OrderItem orderItem) {
    OrderItem savedOrderItem;
    if (orderItem != null) {
      savedOrderItem = orderItemRepository.save(orderItem);
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrderItem;
  }

  public OrderItem update(final OrderItem orderItem) {
    OrderItem savedOrderItem;
    if (orderItem != null) {
      savedOrderItem = orderItemRepository.save(orderItem);
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrderItem;
  }

  public void deleteOrderItemById(final Long id) {
    OrderItem orderItem = findOrderItemById(id);
    orderItemRepository.delete(orderItem);
  }

  /*public void deleteProductFromOrderItem(final Long orderId, final Long productId) {
    OrderItem order = findOrderItemById(orderId);
  }*/

  public long countOrderItem() {
    return orderItemRepository.count();
  }

  public List<OrderItem> findAll(int count) {
    return StreamSupport.stream(orderItemRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }
}
