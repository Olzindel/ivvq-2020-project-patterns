package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.User;
import patterns.backend.services.OrderItemService;
import patterns.backend.services.OrderService;
import patterns.backend.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrderMutation implements GraphQLMutationResolver {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    public Order createOrder(OrderStatus orderStatus, List<Long> orderItemIds, Long userId) {
        Order order = new Order(null, orderStatus, null);
        return orderService.create(order, orderItemIds, userId);
    }

    public Long deleteOrder(Long orderId) {
        orderService.deleteOrderById(orderId);
        return orderId;
    }

    public Order updateOrder(Long orderId, OrderStatus orderStatus, List<Long> orderItemIds, String createdAt, Long userId) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        Order order = orderService.findOrdersById(orderId);
        if (orderItemIds != null && !orderItemIds.isEmpty()) {
            List<Long> toDelete = order.getOrderItems().stream().
                    map(OrderItem::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(orderItemIds);

            for (Long idToAdd : toDelete) {
                orderItemService.deleteOrderItemById(idToAdd);
            }
        }

        if (userId != null) {
            User user = userService.findUserById(userId);
            user.getOrders().remove(order);
        }

        if (orderStatus != null) {
            order.setOrderStatus(orderStatus);
        }

        if (localcreatedAt != null) {
            order.setCreatedAt(localcreatedAt);
        }

        return orderService.update(order);
    }

}
