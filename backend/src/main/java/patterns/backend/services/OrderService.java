package patterns.backend.services;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.User;
import patterns.backend.exception.OrderNotFoundException;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.repositories.OrderRepository;

@Service
@Getter
@Setter
@Transactional
public class OrderService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

  @Autowired private OrderRepository orderRepository;

  @Autowired private OrderItemService orderItemService;

  @Autowired private UserService userService;

  public Order findOrderById(final Long id) {
    Optional<Order> optionalOrders = orderRepository.findById(id);
    if (!optionalOrders.isPresent()) {
      throw new OrderNotFoundException(id);
    } else {
      return optionalOrders.get();
    }
  }

  public Order create(OrderInput orderInput) {
    Order order = new Order(orderInput.getStatus(), null);

    if (orderInput.getUserId() != null) {
      User user = userService.findUserById(orderInput.getUserId());
      order.setUser(user);
    }

    if (orderInput.getOrderItemIds() != null && !orderInput.getOrderItemIds().isEmpty()) {
      Set<OrderItem> orderItems = new HashSet<>();
      for (Long id : orderInput.getOrderItemIds()) {
        orderItems.add(orderItemService.findOrderItemById(id));
      }
      order.setOrderItems(orderItems);
    }

    return create(order);
  }

  public Order create(Order order) {
    Order savedOrder;

    if (order != null) {
      savedOrder = orderRepository.save(order);
      if (order.getOrderItems() != null) {
        for (OrderItem orderItem : order.getOrderItems()) {
          orderItem.setOrder(order);
          orderItemService.decreaseStockIfPaid(orderItem);
        }
        if (order.getUser() != null) {
          order.getUser().addOrder(order);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrder;
  }

  public void deleteOrderById(final Long id) {
    Order order = findOrderById(id);
    for (OrderItem orderItem : order.getOrderItems()) {
      orderItemService.deleteOrderItemById(orderItem.getId());
    }
    order.getUser().getOrders().remove(order);
    orderRepository.delete(order);
  }

  public long countOrders() {
    return orderRepository.count();
  }

  public List<Order> findAll(int count) {
    return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }

  public Order update(Long orderId, OrderInput orderInput) {
    Order order = findOrderById(orderId);
    if (orderInput.getStatus() != null) {
      order.setStatus(orderInput.getStatus());
    }
    if (orderInput.getOrderItemIds() != null && !orderInput.getOrderItemIds().isEmpty()) {
      List<Long> toDelete =
          order.getOrderItems().stream().map(OrderItem::getId).collect(Collectors.toList());

      toDelete.removeAll(orderInput.getOrderItemIds());

      for (Long idToAdd : toDelete) {
        orderItemService.deleteOrderItemById(idToAdd);
      }
    }

    if (orderInput.getUserId() != null) {
      order.getUser().getOrders().remove(order);
      User user = userService.findUserById(orderInput.getUserId());
      order.setUser(user);
    }

    return create(order);
  }
}
