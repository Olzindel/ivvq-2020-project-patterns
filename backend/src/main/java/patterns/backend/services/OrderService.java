package patterns.backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.exception.OrdersNotFoundException;
import patterns.backend.repositories.OrderRepository;

@Service
@Getter
@Setter
@Transactional
public class OrderService {

  @Autowired private OrderRepository orderRepository;

  @Autowired private OrderItemService orderItemService;

  public Order findOrdersById(final Long id) {
    Optional<Order> optionalOrders = orderRepository.findById(id);
    if (!optionalOrders.isPresent()) {
      throw new OrdersNotFoundException(id);
    } else {
      return optionalOrders.get();
    }
  }

  public Order create(final Order order) {
    Order savedOrder;

    if (order != null) {
      order.setCreatedAt(LocalDate.now());
      savedOrder = orderRepository.save(order);
      if (order.getUser() != null) {
        order.getUser().addOrder(order);
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrder;
  }

  public Order update(final Order order) {
    Order savedOrder;

    if (order != null) {
      savedOrder = orderRepository.save(order);
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrder;
  }

  public void deleteOrderById(final Long id) {
    Order order = findOrdersById(id);
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
}
