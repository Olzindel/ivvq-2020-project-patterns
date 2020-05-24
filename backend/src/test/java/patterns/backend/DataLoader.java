package patterns.backend;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.*;
import patterns.backend.graphql.input.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DataLoader {

    User user;
    Product product;
    OrderItem orderItem;
    ImageLink imageLink;
    Order order;

    UserInput userInput;
    ProductInput productInput;
    ImageLinkInput imageLinkInput;
    OrderInput orderInput;
    OrderItemInput orderItemInput;


    public DataLoader() {
        super();
        load();
        loadInput();
    }

    private void load() {
        user = new User("Robert", "Tralala", "robert.Tralala@gmail.com", "M", "51 chemin du Pastis", "51000", "Jaune", false);
        product = new Product("Saber", 100000.0, ProductStatus.AVAILABLE, "Description", 10);
        imageLink = new ImageLink("http://www.l.com", product);
        order = new Order(OrderStatus.PAID, user);
        orderItem = new OrderItem(2, product, order);

        Set<OrderItem> orderItems = new HashSet<>();
        Set<Order> orders = new HashSet<>();
        Set<ImageLink> imageLinks = new HashSet<>();

        orderItems.add(orderItem);
        orders.add(order);
        imageLinks.add(imageLink);

        user.setOrders(orders);
        product.setImageLinks(imageLinks);
        order.setOrderItems(orderItems);
    }

    private void loadInput() {
        userInput = new UserInput("Robert", "Tralala", "robert.Tralala@gmail.com", "M", "51 chemin du Pastis", "51000", "Jaune", false, null);
        productInput = new ProductInput("Saber", Float.parseFloat("100000.0"), ProductStatus.AVAILABLE, "Description", 10, null);
        imageLinkInput = new ImageLinkInput("http://www.l.com", null);
        orderInput = new OrderInput(OrderStatus.PAID, null, null);
        orderItemInput = new OrderItemInput(2, null, null);
    }
}
