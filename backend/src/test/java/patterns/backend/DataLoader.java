package patterns.backend;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.*;
import patterns.backend.graphql.input.*;

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
  String token;
  String unknownToken;

  public DataLoader() {
    super();
    load();
    loadInput();
  }

  private void load() {
    user =
        new User(
            "user",
            "user",
            "Robert",
            "Tralala",
            "robert.Tralala@gmail.com",
            "M",
            "51 chemin du Pastis",
            "51000",
            "Jaune",
            Role.USER);
    product = new Product("Saber", 100000.0, ProductStatus.AVAILABLE, "Description", 10);
    imageLink = new ImageLink("http://www.l.com", product);
    order = new Order(OrderStatus.PAID, user);
    orderItem = new OrderItem(2, product, order);
    token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTkxNTM0NDU3fQ.ah7TMtPvrs1N8QGaquhMOJUjle5kdQuWId0AnsdBy8xvgFly3YNUhujwPD1jrVxCf-aIrntqKhKbj911VMjshQ";
    unknownToken =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU5MTUyNTc0M30.NlyrZtd_irBfoJ3Ej1QFDvXkHYp53vSA7FLH-CfPEXUFhKb42VN51hkjp8Dnz50J7k60VQVGsqkqEkdcF7-P3Q";

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
    userInput =
        new UserInput(
            "user",
            "user",
            "Robert",
            "Tralala",
            "robert.Tralala@gmail.com",
            "M",
            "51 chemin du Pastis",
            "51000",
            "Jaune",
            Role.USER,
            null);
    productInput =
        new ProductInput(
            "Saber",
            Float.parseFloat("100000.0"),
            ProductStatus.AVAILABLE,
            "Description",
            10,
            null);
    imageLinkInput = new ImageLinkInput("http://www.l.com", null);
    orderInput = new OrderInput(OrderStatus.PAID, null, null);
    orderItemInput = new OrderItemInput(2, null, null);
  }
}
