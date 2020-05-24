package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.services.UserService;

@Component
@Transactional
@Getter
@Setter
public class UserMutation implements GraphQLMutationResolver {

  @Autowired UserService userService;

  public User createUser(UserInput userInput) {
    return userService.create(userInput);
  }

  public Long deleteUser(Long userId) {
    userService.deleteUserById(userId);
    return userId;
  }

  public User updateUser(Long userId, UserInput userInput) {
    return userService.update(userId, userInput);
  }
}
