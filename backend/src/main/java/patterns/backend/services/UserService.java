package patterns.backend.services;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.exception.UserNotFoundException;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.repositories.UserRepository;

@Service
@Getter
@Setter
@Transactional
public class UserService {
  @Autowired private UserRepository userRepository;

  @Autowired private OrderService orderService;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User findUserById(final Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      throw new UserNotFoundException(id);
    } else {
      return optionalUser.get();
    }
  }

  public User create(final User user) {
    User savedUser;
    if (user != null) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      savedUser = userRepository.save(user);
      if (user.getOrders() != null) {
        for (Order order : user.getOrders()) {
          order.setUser(user);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedUser;
  }

  public long countUser() {
    return userRepository.count();
  }

  public void deleteUserById(final Long id) {
    User user = findUserById(id);
    for (Order order : user.getOrders()) {
      orderService.deleteOrderById(order.getId());
    }
    userRepository.delete(user);
  }

  public List<User> findAll(int count) {
    // TODO à changer avec de la pagination
    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }

  public User create(UserInput userInput) {
    User user =
        new User(
            userInput.getUsername(),
            userInput.getPassword(),
            userInput.getFirstName(),
            userInput.getLastName(),
            userInput.getEmail(),
            userInput.getGender(),
            userInput.getStreet(),
            userInput.getPostalCode(),
            userInput.getCity(),
            userInput.getRole());

    if (userInput.getOrderIds() != null && !userInput.getOrderIds().isEmpty()) {
      Set<Order> orders = new HashSet<>();
      for (Long id : userInput.getOrderIds()) {
        orders.add(orderService.findOrderById(id));
      }
      user.setOrders(orders);
    }
    return create(user);
  }

  public User update(Long userId, UserInput userInput) {
    User user = findUserById(userId);

    if (userInput.getPassword() != null) {
      user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
    }

    if (userInput.getFirstName() != null) {
      user.setFirstName(userInput.getFirstName());
    }

    if (userInput.getLastName() != null) {
      user.setLastName(userInput.getLastName());
    }

    if (userInput.getEmail() != null) {
      user.setEmail(userInput.getEmail());
    }

    if (userInput.getGender() != null) {
      user.setGender(userInput.getGender());
    }

    if (userInput.getStreet() != null) {
      user.setStreet(userInput.getStreet());
    }

    if (userInput.getPostalCode() != null) {
      user.setPostalCode(userInput.getPostalCode());
    }

    if (userInput.getCity() != null) {
      user.setCity(userInput.getCity());
    }
    if (userInput.getOrderIds() != null && !userInput.getOrderIds().isEmpty()) {
      List<Long> orderIds = new ArrayList<>(userInput.getOrderIds());
      List<Long> toDelete =
          user.getOrders().stream().map(Order::getId).collect(Collectors.toList());

      toDelete.removeAll(userInput.getOrderIds());

      for (Long idToAdd : toDelete) {
        // TODO enlever de la liste
        orderService.deleteOrderById(idToAdd);
      }
      for (Long idToAdd : orderIds) {
        user.addOrder(orderService.findOrderById(idToAdd));
      }
    }
    User savedUser;
    if (user != null) {
      savedUser = userRepository.save(user);
      if (user.getOrders() != null) {
        for (Order order : user.getOrders()) {
          order.setUser(user);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedUser;
  }
}
