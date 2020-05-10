package patterns.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.*;
import patterns.backend.services.*;

import java.util.List;

@Component
@Transactional
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

    @Autowired
    ImageLinkService imageLinkService;

    public List<User> getUsers(final int count) {
        List<User> user = userService.findAll(count);
        return user;
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

    public List<ImageLink> getImageLinks(final int count) {
        return imageLinkService.findAll(count);
    }

    public User getUser(final Long userId) {
        User user = userService.findUserById(userId);
        return user;
    }

    public Merchant getMerchant(final Long merchantId) {
        return merchantService.findMerchantById(merchantId);
    }

    public Product getProduct(final Long productId) {
        return productService.findProductById(productId);
    }

    public OrderItem getOrderItem(final Long orderItemId) {
        return orderItemService.findOrderItemById(orderItemId);
    }

    public Order getOrder(final Long orderId) {
        return orderService.findOrdersById(orderId);
    }

    public ImageLink getImageLink(final Long imageLinkId) {
        return imageLinkService.findImageLinkById(imageLinkId);
    }
}