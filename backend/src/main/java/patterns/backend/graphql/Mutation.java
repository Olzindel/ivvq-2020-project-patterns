package patterns.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.*;
import patterns.backend.services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class Mutation implements GraphQLMutationResolver {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

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

    public User createUser(String fullName, String email, String gender, String dateOfBirth, String createdAt) {
        LocalDate localDateOfBirth = dateOfBirth != null ? LocalDate.parse(dateOfBirth, formatter) : null;
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        User user = new User(fullName, email, gender, localDateOfBirth, localcreatedAt);
        return userService.create(user);
    }

    public Merchant createMerchant(String name, Long id, String createdAt) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        User admin = userService.findUserById(id);
        Merchant merchant = new Merchant(name, localcreatedAt, admin);
        return merchantService.create(merchant);
    }

    public Product createProduct(String name, float price, String status, String imageLink, Long merchantId) {
        Merchant merchant = merchantService.findMerchantById(merchantId);
        Product product = new Product(name, price, status, LocalDate.now(), imageLink, merchant);
        return productService.create(product);
    }

    public Order createOrder(OrderStatus orderStatus, List<Long> orderItemIds, Long userId) {
        User user = userService.findUserById(userId);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Long id : orderItemIds) {
            orderItems.add(orderItemService.findOrderItemById(id));
        }
        Order order = new Order(LocalDate.now(), orderStatus, user);
        order.setOrderItems(orderItems);
        return orderService.create(order);
    }

    public OrderItem createOrderItem(int quantity, Long productId, Long orderId) {
        Product product = productService.findProductById(productId);
        Order order = orderService.findOrdersById(orderId);
        OrderItem orderItem = new OrderItem(quantity, product, order);
        return orderItemService.create(orderItem);
    }

    public Long deleteUser(Long userId) {
        userService.deleteUserById(userId);
        return userId;
    }

    public Long deleteMerchant(Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
        return merchantId;
    }

    public Long deleteOrder(Long orderId) {
        orderService.deleteOrderById(orderId);
        return orderId;
    }

    public Long deleteOrderItem(Long orderItemId) {
        orderItemService.deleteOrderItemById(orderItemId);
        return orderItemId;
    }

    public Long deleteProduct(Long productId) {
        productService.deleteProductById(productId);
        return productId;
    }
}