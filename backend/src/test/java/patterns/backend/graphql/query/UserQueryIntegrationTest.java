package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.graphql.mutation.MerchantMutation;
import patterns.backend.graphql.mutation.OrderMutation;
import patterns.backend.graphql.mutation.UserMutation;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserQueryIntegrationTest {

    @Autowired
    UserQuery userQuery;

    @Autowired
    UserMutation userMutation;

    @Autowired
    OrderMutation orderMutation;

    @Autowired
    MerchantMutation merchantMutation;

    UserInput userInput;
    MerchantInput merchantInput;
    OrderInput orderInput;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        userInput = dataLoader.getUserInput();
        merchantInput = dataLoader.getMerchantInput();
        orderInput = dataLoader.getOrderInput();
    }

    @Test
    void getUsers() {
        Merchant merchant = merchantMutation.createMerchant(merchantInput);
        Order order = orderMutation.createOrder(orderInput);
        userInput.setOrderIds(Arrays.asList(order.getId()));
        userInput.setMerchantIds(Arrays.asList(merchant.getId()));
        User user = userMutation.createUser(userInput);

        List<User> usersQueried = userQuery.getUsers(2);

        for (User userQueried : usersQueried) {
            assertEquals(user.getMerchants(), userQueried.getMerchants());
            assertEquals(user.getOrders(), userQueried.getOrders());
            assertEquals(user.getStreet(), userQueried.getStreet());
            assertEquals(user.getCity(), userQueried.getCity());
            assertEquals(user.getPostalCode(), userQueried.getPostalCode());
            assertEquals(user.getEmail(), userQueried.getEmail());
            assertEquals(user.getGender(), userQueried.getGender());
            assertEquals(user.getFirstName(), userQueried.getFirstName());
            assertEquals(user.getLastName(), userQueried.getLastName());
        }
    }

    @Test
    void getUser() {
        Merchant merchant = merchantMutation.createMerchant(merchantInput);
        Order order = orderMutation.createOrder(orderInput);
        userInput.setOrderIds(Arrays.asList(order.getId()));
        userInput.setMerchantIds(Arrays.asList(merchant.getId()));
        User user = userMutation.createUser(userInput);

        User userQueried = userQuery.getUser(user.getId());

        assertEquals(user.getMerchants(), userQueried.getMerchants());
        assertEquals(user.getOrders(), userQueried.getOrders());
        assertEquals(user.getStreet(), userQueried.getStreet());
        assertEquals(user.getCity(), userQueried.getCity());
        assertEquals(user.getPostalCode(), userQueried.getPostalCode());
        assertEquals(user.getEmail(), userQueried.getEmail());
        assertEquals(user.getGender(), userQueried.getGender());
        assertEquals(user.getFirstName(), userQueried.getFirstName());
        assertEquals(user.getLastName(), userQueried.getLastName());
    }
}
