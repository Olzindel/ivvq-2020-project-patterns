package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.Order;
import patterns.backend.domain.Product;
import patterns.backend.services.OrderService;

import java.util.List;

@Component
@Getter
@Setter
public class OrderQuery implements GraphQLQueryResolver {
    @Autowired
    OrderService orderService;

    public List<Order> getOrders(final int count) {
        return orderService.findAll(count);
    }

    public Order getOrder(final Long orderId) {
        return orderService.findOrderById(orderId);
    }

    public List<Product> enoughStock(Long orderId) {
        return orderService.enoughStock(orderId);
    }
}
