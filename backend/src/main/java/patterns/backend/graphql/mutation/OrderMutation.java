package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.services.OrderService;

@Component
@Transactional
@Getter
@Setter
public class OrderMutation implements GraphQLMutationResolver {

    @Autowired
    private OrderService orderService;

    public Order createOrder(OrderInput orderInput) {
        return orderService.create(orderInput);
    }

    public Long deleteOrder(Long orderId) {
        orderService.deleteOrderById(orderId);
        return orderId;
    }

    public Order updateOrder(Long orderId, OrderInput orderInput) {
        return orderService.update(orderId, orderInput);
    }

}
