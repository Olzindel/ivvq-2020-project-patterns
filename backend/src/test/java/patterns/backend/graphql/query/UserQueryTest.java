package patterns.backend.graphql.query;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserQueryTest {

  @MockBean UserService userService;

  UserQuery userQuery;

  @BeforeEach
  public void setup() {
    userQuery = new UserQuery();
    userQuery.setUserService(userService);
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
}
