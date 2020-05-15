package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.services.MerchantService;
import patterns.backend.services.OrderService;
import patterns.backend.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserMutation implements GraphQLMutationResolver {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @Autowired
    UserService userService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    OrderService orderService;



    public User createUser(String firstName, String lastName, String email, String gender, String dateOfBirth, String street, String postalCode, String city, List<Long> merchantIds, List<Long> orderIds) {
        LocalDate localDateOfBirth = dateOfBirth != null ? LocalDate.parse(dateOfBirth, formatter) : null;
        User user = new User(firstName, lastName, email, gender, localDateOfBirth, street, postalCode, city, null);
        return userService.create(user, merchantIds, orderIds);
    }

    public Long deleteUser(Long userId) {
        userService.deleteUserById(userId);
        return userId;
    }

    public User updateUser(Long userId, String firstName, String lastName, String email, String gender, String dateOfBirth, String street, String postalCode, String city, String createdAt, List<Long> merchantIds, List<Long> orderIds) {
        User user = userService.findUserById(userId);
        LocalDate localDateOfBirth = dateOfBirth != null ? LocalDate.parse(dateOfBirth, formatter) : null;
        LocalDate localcreatedAt = createdAt != null ? LocalDate.parse(createdAt, formatter) : null;

        if (firstName != null) {
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
        if (street != null) {
            user.setStreet(street);
        }
        if (postalCode != null) {
            user.setPostalCode(postalCode);
        }
        if (city != null) {
            user.setCity(city);
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
                // TODO enlever de la liste
                orderService.deleteOrderById(idToAdd);
            }
        }

        user = userService.update(user);
        return user;
    }
}
