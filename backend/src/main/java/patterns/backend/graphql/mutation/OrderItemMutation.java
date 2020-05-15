package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.Product;
import patterns.backend.services.OrderItemService;
import patterns.backend.services.OrderService;
import patterns.backend.services.ProductService;

@Component
@Transactional
public class OrderItemMutation implements GraphQLMutationResolver {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public OrderItem createOrderItem(int quantity, Long productId, Long orderId) {
        OrderItem orderItem = new OrderItem(quantity, null, null);
        return orderItemService.create(orderItem, productId, orderId);
    }

    public Long deleteOrderItem(Long orderItemId) {
        orderItemService.deleteOrderItemById(orderItemId);
        return orderItemId;
    }

    public OrderItem updateOrderItem(Long orderItemId, Integer quantity, Long productId, Long orderId) {
        OrderItem orderItem = orderItemService.findOrderItemById(orderItemId);
        if (productId != null) {
            Product product = productService.findProductById(productId);
            orderItem.setProduct(product);
        }
        if (orderId != null) {
            Order order = orderService.findOrdersById(orderId);
            order.getOrderItems().remove(orderItem);
            orderItem.setOrder(order);
        }
        if (quantity != null) {
            orderItem.setQuantity(quantity);
        }

        return orderItemService.update(orderItem);
    }
}
