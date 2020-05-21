package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.exception.UserNotFoundException;
import patterns.backend.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



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
            user.setCreatedAt(LocalDate.now());
            savedUser = userRepository.save(user);
            if (user.getMerchants() != null) {
                for (Merchant merchant : user.getMerchants()) {
                    merchant.setAdmin(user);
                }
            }
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

    public User update(final User user) {
        User savedUser;
        if (user != null) {
            savedUser = userRepository.save(user);
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
        List<Merchant> merchants = merchantService.findMerchantByUser(user.getId());
        for (Merchant merchant : merchants) {
            merchantService.deleteMerchantById(merchant.getId());
        }
        userRepository.delete(user);
    }

    public List<User> findAll(int count) {
        // TODO à changer avec de la pagination
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .limit(count)
                .collect(Collectors.toList());
    }

    public User create(User user, List<Long> merchantIds, List<Long> orderIds) {
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
        return create(user);
    }
}