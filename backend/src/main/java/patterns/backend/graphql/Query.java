package patterns.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.*;
import patterns.backend.services.*;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    UserService userService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    public List<User> getUsers(final int count) {
        return userService.findAll(count);
    }

    public List<Merchant> getMerchants(final int count) {
        return merchantService.findAll(count);
    }

    public List<Product> getProducts(final int count) {
        return productService.findAll(count);
    }

    public List<OrderItem> getOrderItems(final int count) {
        return orderItemService.findAll(count);
    }

    public List<Order> getOrders(final int count) {
        return orderService.findAll(count);
    }

    public User getUser(final Long id) {
        return userService.findUserById(id);
    }

    public Merchant getMerchant(final Long id) {
        return merchantService.findMerchantById(id);
    }

    public Product getProduct(final Long id) {
        return productService.findProductById(id);
    }

    public OrderItem getOrderItem(final Long id) {
        return orderItemService.findOrderItemById(id);
    }

    public Order getOrder(final Long id) {
        return orderService.findOrdersById(id);
    }
}