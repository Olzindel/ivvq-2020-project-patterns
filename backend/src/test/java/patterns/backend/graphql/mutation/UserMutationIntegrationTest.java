package patterns.backend.graphql.mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.User;
import patterns.backend.exception.OrderNotFoundException;
import patterns.backend.graphql.input.OrderInput;
import patterns.backend.graphql.input.UserInput;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserMutationIntegrationTest {

    @Autowired
    UserMutation userMutation;

    @Autowired
    OrderMutation orderMutation;


    UserInput userInput;
    OrderInput orderInput;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        userInput = dataLoader.getUserInput();
        orderInput = dataLoader.getOrderInput();
    }

    @Test
    void createUser() {
        Order order = orderMutation.createOrder(orderInput);
        userInput.setOrderIds(Arrays.asList(order.getId()));

        User user = userMutation.createUser(userInput);

        assertEquals(userInput.getCity(), user.getCity());
        assertEquals(userInput.getEmail(), user.getEmail());
        assertEquals(userInput.getFirstName(), user.getFirstName());
        assertEquals(userInput.getGender(), user.getGender());
        assertEquals(userInput.getLastName(), user.getLastName());
        assertEquals(userInput.getPostalCode(), user.getPostalCode());
        assertEquals(userInput.getStreet(), user.getStreet());
        assertEquals(user, orderMutation.getOrderService().findOrderById(order.getId()).getUser());

    }

    @Test
    void deleteUser() {
        Order order = orderMutation.createOrder(orderInput);
        userInput.setOrderIds(Arrays.asList(order.getId()));
        User user = userMutation.createUser(userInput);
        long count = userMutation.getUserService().countUser();

        userMutation.deleteUser(user.getId());

        assertEquals(count - 1, userMutation.getUserService().countUser());
        assertThrows(OrderNotFoundException.class, () -> orderMutation.getOrderService().findOrderById(order.getId()));
    }

    @Test
    void updateUser() {
        Order order = orderMutation.createOrder(orderInput);
        userInput.setOrderIds(Arrays.asList(order.getId()));
        User user = userMutation.createUser(userInput);
        long count = userMutation.getUserService().countUser();

        UserInput userInputUpdate = new UserInput();

        OrderInput orderInputUpdate = new OrderInput(OrderStatus.ABORTED, null, null);
        Order orderUpdate = orderMutation.createOrder(orderInputUpdate);


        userInputUpdate.setCity("p");
        userInputUpdate.setStreet("p");
        userInputUpdate.setPostalCode("31450");
        userInputUpdate.setLastName("p");
        userInputUpdate.setFirstName("p");
        userInputUpdate.setEmail("t.t@gmail.com");
        userInputUpdate.setGender("F");
        List<Long> orderIds = Arrays.asList(orderUpdate.getId());
        userInputUpdate.setOrderIds(orderIds);

        User userUpdated = userMutation.updateUser(user.getId(), userInputUpdate);

        assertEquals("p", userUpdated.getCity());
        assertEquals("t.t@gmail.com", userUpdated.getEmail());
        assertEquals("p", userUpdated.getFirstName());
        assertEquals("F", userUpdated.getGender());
        assertEquals("p", userUpdated.getLastName());
        assertEquals("t.t@gmail.com", userUpdated.getEmail());
        assertEquals("p", userUpdated.getStreet());
        for (Order o : userUpdated.getOrders()) {
            assert orderIds.contains(o.getId());
        }
    }
}
