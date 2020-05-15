package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.User;
import patterns.backend.services.UserService;

import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {
    @Autowired
    UserService userService;

    public List<User> getUsers(final int count) {
        return userService.findAll(count);
    }

    public User getUser(final Long userId) {
        return userService.findUserById(userId);
    }
}
