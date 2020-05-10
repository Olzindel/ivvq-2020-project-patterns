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
import java.util.stream.Collectors;

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

    @Autowired
    ImageLinkService imageLinkService;

    public User createUser(String firstName, String lastName, String email, String gender, String dateOfBirth, String rue, String codePostal, String ville, String createdAt, List<Long> merchantIds, List<Long> orderIds) {
        LocalDate localDateOfBirth = dateOfBirth != null ? LocalDate.parse(dateOfBirth, formatter) : null;
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        List<Merchant> merchants = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        if (merchantIds != null) {
            for (Long id : merchantIds) {
                merchants.add(merchantService.findMerchantById(id));
            }
        }
        if (orderIds != null) {
            for (Long id : orderIds) {
                orders.add(orderService.findOrdersById(id));
            }
        }
        User user = new User(firstName, lastName, email, gender, localDateOfBirth, rue, codePostal, ville, localcreatedAt);
        return userService.create(user);
    }

    public Merchant createMerchant(String name, Long id, String createdAt) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        User admin = userService.findUserById(id);
        Merchant merchant = new Merchant(name, localcreatedAt, admin);
        return merchantService.create(merchant);
    }

    public Product createProduct(String name, float price, String status, String description, int stock, List<Long> imageLinkIds, Long merchantId) {
        Merchant merchant = merchantService.findMerchantById(merchantId);
        List<ImageLink> imageLinks = new ArrayList<>();
        if (imageLinkIds != null) {
            for (Long id : imageLinkIds) {
                imageLinks.add(imageLinkService.findImageLinkById(id));
            }
        }
        Product product = new Product(name, price, status, description, stock, LocalDate.now(), merchant);
        product.setImageLinks(imageLinks);
        return productService.create(product);
    }

    public ImageLink createImageLink(String imageLink, Long productId) {
        Product product = productService.findProductById(productId);
        return imageLinkService.create(new ImageLink(imageLink, product));
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

    public Long deleteImageLink(Long imageLinkId) {
        imageLinkService.deleteImageLinkById(imageLinkId);
        return imageLinkId;
    }

    public ImageLink updateImageLink(Long imageLinkId, String imageLinkString, Long productId) {
        ImageLink imageLink = imageLinkService.findImageLinkById(imageLinkId);
        if (productId != null) {
            imageLink.getProduct().getImageLinks().remove(imageLink);
            Product product = productService.findProductById(productId);
            imageLink.setProduct(product);
        }

        if (imageLink != null) {
            imageLink.setImageLink(imageLinkString);
        }

        return imageLinkService.update(imageLink);
    }

    public Merchant updateMerchant(Long merchantId, String name, Long userId, String createdAt) {
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;
        Merchant merchant = merchantService.findMerchantById(merchantId);
        if (userId != null) {
            merchant.getAdmin().getMerchants().remove(merchant);
            User user = userService.findUserById(userId);
            merchant.setAdmin(user);
        }

        if (name != null) {
            merchant.setName(name);
        }

        if (localcreatedAt != null) {
            merchant.setCreatedAt(localcreatedAt);
        }

        return merchantService.update(merchant);
    }

    public Order updateOrder(Long orderId, OrderStatus orderStatus, List<Long> orderItemIds, Long userId) {
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

        return orderService.update(order);
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

    public Product updateProduct(long productId, String name, Float price, String status, String description, Integer stock, List<Long> imageLinkIds, Long merchantId) {
        Product product = productService.findProductById(productId);

        if (imageLinkIds != null && !imageLinkIds.isEmpty()) {
            List<Long> toDelete = product.getImageLinks().stream().
                    map(ImageLink::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(imageLinkIds);

            for (Long idToAdd : toDelete) {
                imageLinkService.deleteImageLinkById(idToAdd);
            }
        }
        if (merchantId != null) {
            Merchant merchant = merchantService.findMerchantById(merchantId);
            product.getMerchant().getProducts().remove(product);
            product.setMerchant(merchant);
        }
        if (name != null) {
            product.setName(name);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (status != null) {
            product.setStatus(status);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (stock != null) {
            product.setStock(stock);
        }
        return productService.update(product);
    }

    public User updateUser(Long userId, String firstName, String lastName, String email, String gender, String dateOfBirth, String rue, String codePostal, String ville, String createdAt, List<Long> merchantIds, List<Long> orderIds) {
        User user = userService.findUserById(userId);
        LocalDate localDateOfBirth = dateOfBirth != null ? LocalDate.parse(dateOfBirth, formatter) : null;
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;

        if (lastName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        if (dateOfBirth != null) {
            user.setDateOfBirth(localDateOfBirth);
        }
        if (localcreatedAt != null) {
            user.setCreatedAt(localcreatedAt);
        }
        if (rue != null) {
            user.setRue(rue);
        }
        if (codePostal != null) {
            user.setCodePostal(codePostal);
        }
        if (ville != null) {
            user.setVille(ville);
        }
        if (merchantIds != null && !merchantIds.isEmpty()) {
            List<Long> toDelete = user.getMerchants().stream().
                    map(Merchant::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(merchantIds);

            for (Long idToAdd : toDelete) {
                merchantService.deleteMerchantById(idToAdd);
            }
        }
        if (orderIds != null && !orderIds.isEmpty()) {
            List<Long> toDelete = user.getOrders().stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());

            toDelete.removeAll(orderIds);

            for (Long idToAdd : toDelete) {
                // TODO enelver de la liste
                orderService.deleteOrderById(idToAdd);
            }
        }

        return userService.update(user);
    }
}