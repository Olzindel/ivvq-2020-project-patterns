package patterns.backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.User;
import patterns.backend.exception.UserNotFoundException;
import patterns.backend.repositories.UserRepository;

@Service
@Getter
@Setter
public class UserService {
  @Autowired private UserRepository userRepository;

  @Autowired private MerchantService merchantService;

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
      user.setCreatedAt(LocalDate.now());
      savedUser = userRepository.save(user);
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
}
