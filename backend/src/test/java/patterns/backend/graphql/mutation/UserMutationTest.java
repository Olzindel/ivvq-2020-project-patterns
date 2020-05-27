package patterns.backend.graphql.mutation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserMutationTest {

  @MockBean UserService userService;

  UserMutation userMutation;
  UserInput userInput;

  @BeforeEach
  public void setup() {
    userMutation = new UserMutation();
    userMutation.setUserService(userService);
    userInput = new UserInput();
  }

  @Test
  void createUser() {
    userMutation.createUser(userInput);
    verify(userService).create(userInput);
  }

  @Test
  void deleteUser() {
    userMutation.deleteUser(0L);
    verify(userService).deleteUserById(0L);
  }

  @Test
  void updateUser() {
    userMutation.updateUser(0L, userInput);
    verify(userService).update(0L, userInput);
  }
}
