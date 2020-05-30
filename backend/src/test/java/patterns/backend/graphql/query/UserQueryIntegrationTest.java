package patterns.backend.graphql.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.graphql.mutation.OrderMutation;
import patterns.backend.graphql.mutation.UserMutation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserQueryIntegrationTest {

  @Autowired UserQuery userQuery;

  @Autowired UserMutation userMutation;

  @Autowired OrderMutation orderMutation;

  UserInput userInput;
  OrderInput orderInput;
  String token;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    userInput = dataLoader.getUserInput();
    orderInput = dataLoader.getOrderInput();
    token = dataLoader.getToken();
  }

  @Test
  void getUsers() {
    Order order = orderMutation.createOrder(orderInput);
    userInput.setOrderIds(Arrays.asList(order.getId()));
    User user = userMutation.createUser(userInput);

    List<User> usersQueried = userQuery.getUsers(2);

    for (User userQueried : usersQueried) {
      assertEquals(user.getOrders(), userQueried.getOrders());
      assertEquals(user.getStreet(), userQueried.getStreet());
      assertEquals(user.getCity(), userQueried.getCity());
      assertEquals(user.getPostalCode(), userQueried.getPostalCode());
      assertEquals(user.getEmail(), userQueried.getEmail());
      assertEquals(user.getGender(), userQueried.getGender());
      assertEquals(user.getFirstName(), userQueried.getFirstName());
      assertEquals(user.getLastName(), userQueried.getLastName());
      assertEquals(user.getRole(), userQueried.getRole());
      assertEquals(user.getUsername(), userQueried.getUsername());
      assertEquals(user.getPassword(), userQueried.getPassword());
    }
  }

  @Test
  void getUser() {
    Order order = orderMutation.createOrder(orderInput);
    userInput.setOrderIds(Arrays.asList(order.getId()));
    User user = userMutation.createUser(userInput);

    User userQueried = userQuery.getUser(user.getId());

    assertEquals(user.getOrders(), userQueried.getOrders());
    assertEquals(user.getStreet(), userQueried.getStreet());
    assertEquals(user.getCity(), userQueried.getCity());
    assertEquals(user.getPostalCode(), userQueried.getPostalCode());
    assertEquals(user.getEmail(), userQueried.getEmail());
    assertEquals(user.getGender(), userQueried.getGender());
    assertEquals(user.getFirstName(), userQueried.getFirstName());
    assertEquals(user.getLastName(), userQueried.getLastName());
    assertEquals(user.getRole(), userQueried.getRole());
    assertEquals(user.getUsername(), userQueried.getUsername());
    assertEquals(user.getPassword(), userQueried.getPassword());
  }

  @Test
  void getUserFromToken() {
    Order order = orderMutation.createOrder(orderInput);
    userInput.setOrderIds(Arrays.asList(order.getId()));
    User user = userMutation.createUser(userInput);

    User userQueried = userQuery.getUserFromToken(token);

    assertEquals(user.getOrders(), userQueried.getOrders());
    assertEquals(user.getStreet(), userQueried.getStreet());
    assertEquals(user.getCity(), userQueried.getCity());
    assertEquals(user.getPostalCode(), userQueried.getPostalCode());
    assertEquals(user.getEmail(), userQueried.getEmail());
    assertEquals(user.getGender(), userQueried.getGender());
    assertEquals(user.getFirstName(), userQueried.getFirstName());
    assertEquals(user.getLastName(), userQueried.getLastName());
    assertEquals(user.getRole(), userQueried.getRole());
    assertEquals(user.getUsername(), userQueried.getUsername());
    assertEquals(user.getPassword(), userQueried.getPassword());
  }
}
