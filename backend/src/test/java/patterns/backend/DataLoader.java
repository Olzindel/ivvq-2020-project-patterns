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
    Merchant merchant;
    Product product;
    OrderItem orderItem;
    ImageLink imageLink;
    Order order;

    UserInput userInput;
    MerchantInput merchantInput;
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
        user = new User("Robert", "Tralala", "robert.Tralala@gmail.com", "M", "51 chemin du Pastis", "51000", "Jaune");
        merchant = new Merchant("Market", user);
        product = new Product("Saber", 100000.0, ProductStatus.AVAILABLE, "Description", 10, merchant);
        imageLink = new ImageLink("http://www.l.com", product);
        order = new Order(OrderStatus.PAID, user);
        orderItem = new OrderItem(2, product, order);

        Set<OrderItem> orderItems = new HashSet<>();
        Set<Merchant> merchants = new HashSet<>();
        Set<Order> orders = new HashSet<>();
        Set<Product> products = new HashSet<>();
        Set<ImageLink> imageLinks = new HashSet<>();

        orderItems.add(orderItem);
        merchants.add(merchant);
        orders.add(order);
        products.add(product);
        imageLinks.add(imageLink);

        user.setMerchants(merchants);
        user.setOrders(orders);
        merchant.setProducts(products);
        product.setImageLinks(imageLinks);
        order.setOrderItems(orderItems);
    }

    private void loadInput() {
        userInput = new UserInput("Robert", "Tralala", "robert.Tralala@gmail.com", "M", "51 chemin du Pastis", "51000", "Jaune", null, null);
        merchantInput = new MerchantInput("Market", null, null);
        productInput = new ProductInput("Saber", Float.parseFloat("100000.0"), ProductStatus.AVAILABLE, "Description", 10, null, null);
        imageLinkInput = new ImageLinkInput("http://www.l.com", null);
        orderInput = new OrderInput(OrderStatus.PAID, null, null);
        orderItemInput = new OrderItemInput(2, null, null);

    }
}
