package patterns.backend.graphql.query;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.DataLoader;
import patterns.backend.security.UserDetailsServiceImpl;
import patterns.backend.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserQueryTest {

  @MockBean UserService userService;

  @MockBean UserDetailsServiceImpl userDetailsService;

  UserQuery userQuery;

  String token;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    userQuery = new UserQuery();
    userQuery.setUserService(userService);
    userQuery.setUserDetailsService(userDetailsService);
    String token = dataLoader.getToken();
  }

  @Test
  void getUsers() {
    userQuery.getUsers(1);
    verify(userService).findAll(1);
  }

  @Test
  void getUser() {
    userQuery.getUser(0L);
    verify(userService).findUserById(0L);
  }

  @Test
  void getUserFromToken() {
    userQuery.getUserFromToken(token);
    verify(userDetailsService).getUserFromToken(token);
  }
}
