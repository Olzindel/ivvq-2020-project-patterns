package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.Order;
import patterns.backend.services.OrderService;

import java.util.List;

@Component
public class OrderQuery implements GraphQLQueryResolver {
    @Autowired
    OrderService orderService;

    public List<Order> getOrders(final int count) {
        return orderService.findAll(count);
    }

    public Order getOrder(final Long orderId) {
        return orderService.findOrdersById(orderId);
    }
}
