package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.User;
import patterns.backend.security.UserDetailsServiceImpl;
import patterns.backend.services.UserService;

@Component
@Getter
@Setter
public class UserQuery implements GraphQLQueryResolver {
  @Autowired UserService userService;

  @Autowired UserDetailsServiceImpl userDetailsService;

  public List<User> getUsers(final int count) {
    return userService.findAll(count);
  }

  public User getUser(final Long userId) {
    return userService.findUserById(userId);
  }

  public User getUserFromToken(final String token) {
    return userDetailsService.getUserFromToken(token);
  }
}
