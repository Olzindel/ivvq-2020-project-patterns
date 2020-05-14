package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.User;
import patterns.backend.exception.OrdersNotFoundException;
import patterns.backend.repositories.OrderRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

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
            if (order.getOrderItems() != null) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItem.setOrder(order);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return savedOrder;
    }

    public Order update(final Order order) {
        Order savedOrder;
        if (order != null) {
            order.setCreatedAt(LocalDate.now());
            savedOrder = orderRepository.save(order);
            if (order.getOrderItems() != null) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItem.setOrder(order);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return savedOrder;
    }

    public void deleteOrderById(final Long id) {
        Order order = findOrdersById(id);
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

    public Order create(Order order, List<Long> orderItemIds, Long userId) {
        User user = userService.findUserById(userId);
        Set<OrderItem> orderItems = new HashSet<>();
        for (Long id : orderItemIds) {
            orderItems.add(orderItemService.findOrderItemById(id));
        }
        order.setOrderItems(orderItems);
        order.setUser(user);
        return create(order);
    }
}
