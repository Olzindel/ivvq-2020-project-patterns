package patterns.backend.services;

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
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.Product;
import patterns.backend.exception.OrderItemNotFoundException;
import patterns.backend.graphql.input.OrderItemInput;
import patterns.backend.repositories.OrderItemRepository;

@Service
@Getter
@Setter
@Transactional
public class OrderItemService {

  @Autowired private OrderItemRepository orderItemRepository;

  @Autowired private ProductService productService;

  @Autowired private OrderService orderService;

  public OrderItem findOrderItemById(final Long id) {
    Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
    if (!optionalOrderItem.isPresent()) {
      throw new OrderItemNotFoundException(id);
    } else {
      return optionalOrderItem.get();
    }
  }

  public OrderItem create(OrderItemInput orderItemInput) {
    OrderItem orderItem = new OrderItem(orderItemInput.getQuantity(), null, null);

    if (orderItemInput.getProductId() != null) {
      Product product = productService.findProductById(orderItemInput.getProductId());
      orderItem.setProduct(product);
    }
    if (orderItemInput.getOrderId() != null) {
      Order order = orderService.findOrderById(orderItemInput.getOrderId());
      orderItem.setOrder(order);
    }

    return create(orderItem);
  }

  public OrderItem create(OrderItem orderItem) {
    OrderItem savedOrderItem;

    if (orderItem != null) {
      decreaseStockIfPaid(orderItem);
      savedOrderItem = orderItemRepository.save(orderItem);
      if (orderItem.getOrder() != null) {
        orderItem.getOrder().addOrderItem(orderItem);
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedOrderItem;
  }

  public OrderItem update(Long orderItemId, OrderItemInput orderItemInput) {
    OrderItem orderItem = findOrderItemById(orderItemId);
    if (orderItemInput.getQuantity() != null) {
      orderItem.setQuantity(orderItemInput.getQuantity());
    }
    if (orderItemInput.getProductId() != null) {
      Product product = productService.findProductById(orderItemInput.getProductId());
      orderItem.setProduct(product);
    }
    if (orderItemInput.getOrderId() != null) {
      orderItem.getOrder().getOrderItems().remove(orderItem);
      Order order = orderService.findOrderById(orderItemInput.getOrderId());
      orderItem.setOrder(order);
    }
    return create(orderItem);
  }

  public void deleteOrderItemById(final Long id) {
    OrderItem orderItem = findOrderItemById(id);
    orderItem.getOrder().removeOrderItem(orderItem);
    orderItemRepository.delete(orderItem);
  }

  public long countOrderItem() {
    return orderItemRepository.count();
  }

  public List<OrderItem> findAll(int count) {
    return StreamSupport.stream(orderItemRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }

  public void decreaseStockIfPaid(OrderItem orderItem) {
    if (orderItem.getOrder() != null && orderItem.getProduct() != null) {
      Product product = orderItem.getProduct();
      if (orderItem.getOrder().getStatus().equals(OrderStatus.PAID)) {
        product.decreaseStock(orderItem.getQuantity());
      }
    }
  }
}
