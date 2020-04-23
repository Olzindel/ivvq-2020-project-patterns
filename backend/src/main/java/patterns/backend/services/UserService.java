package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.User;
import patterns.backend.exception.UserNotFoundException;
import patterns.backend.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(id);
        } else {
            return optionalUser.get();
        }
    }

    public User saveUser(User user) {
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

    public void deleteUserById(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}