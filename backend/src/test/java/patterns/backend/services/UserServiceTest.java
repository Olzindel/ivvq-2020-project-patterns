package patterns.backend.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import patterns.backend.DataLoader;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.UserInput;
import patterns.backend.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceTest {

  private UserService userService;

  @MockBean private UserRepository userRepository;

  @MockBean private OrderService orderService;

  @MockBean private BCryptPasswordEncoder bCryptPasswordEncoder;

  private User user;

  private UserInput userInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();

    userService = new UserService();
    userService.setOrderService(orderService);
    userService.setUserRepository(userRepository);
    userService.setBCryptPasswordEncoder(bCryptPasswordEncoder);

    user = dataLoader.getUser();
    userInput = dataLoader.getUserInput();
  }

  @Test
  public void testTypeRepository() {
    // the associated Repository of an UserService is type of CrudRepository
    assertThat(userService.getUserRepository(), instanceOf(CrudRepository.class));
  }

  @Test
  void findUserById() {
    // given: an user and an UserService
    when(userService.getUserRepository().findById(0L)).thenReturn(java.util.Optional.of(user));
    // when: the findAll method is invoked
    userService.findUserById(0L);
    // then: the findAll method of the Repository is invoked
    verify(userService.getUserRepository()).findById(0L);
  }

  @Test
  void saveUser() {
    // given: an user and an userService
    when(userService.getUserRepository().save(user)).thenReturn(user);
    // when: saveUser is invoked
    userService.create(user);
    // then: the save method of UserRepository is invoked
    verify(userService.getUserRepository()).save(user);
  }

  @Test
  void createUser() {
    // given: an user and an userService
    when(userService.getUserRepository().save(user)).thenReturn(user);

    // when: saveUser is invoked
    userService.create(user);

    // then: the save method of UserRepository is invoked
    verify(userService.getUserRepository()).save(user);
  }

  @Test
  void createUserFromUserInput() {
    // given: an userInput and an userService
    UserService userServiceMock = mock(UserService.class);
    doCallRealMethod().when(userServiceMock).create(userInput);
    // when: create(UserInput) method is invoked
    userServiceMock.create(userInput);
    // then: create(User) method is invoked
    verify(userServiceMock).create(userInput);
  }

  @Test
  void update() {
    // given: an userInput, an User and an userService
    when(userService.getUserRepository().findById(0L))
        .thenReturn(java.util.Optional.ofNullable(user));
    when(userRepository.save(user)).thenReturn(user);
    // when: update method is invoked
    User userUpdated = userService.update(0L, userInput);
    // then: all fields are updated
    assertEquals(user.getId(), userUpdated.getId());
    assertEquals(user.getUsername(), userUpdated.getUsername());
    assertEquals(user.getRole(), userUpdated.getRole());
    assertEquals(user.getOrders(), userUpdated.getOrders());
    assertEquals(user.getEmail(), userUpdated.getEmail());
    assertEquals(user.getFirstName(), userUpdated.getFirstName());
    assertEquals(user.getLastName(), userUpdated.getLastName());
    assertEquals(user.getGender(), userUpdated.getGender());
    assertEquals(user.getCity(), userUpdated.getCity());
    assertEquals(user.getStreet(), userUpdated.getStreet());
    assertEquals(user.getPostalCode(), userUpdated.getPostalCode());
    // then : save method of userRepository is called
    verify(userRepository).save(user);
    // then : findById method of userRepository is called
    verify(userRepository).findById(0L);
  }

  @Test
  void countUser() {
    // given: an UserService
    // when: the count method is invoked
    userService.countUser();
    // then: the count method of the Repository is invoked
    verify(userService.getUserRepository()).count();
  }

  @Test
  void deleteUser() {
    // given: an UserService and an user persisted
    user.setId(0L);
    when(userService.getUserRepository().findById(0L)).thenReturn(java.util.Optional.of(user));
    // when: the deleteUserById method is invoked
    userService.deleteUserById(0L);
    // then: the delete method of the Repository is invoked
    verify(userService.getUserRepository()).delete(user);
  }

  @Test
  void findAll() {
    // given: an UserService
    // when: the findAll method is invoked
    userService.findAll(8);
    // then: the findAll method of the Repository is invoked
    verify(userService.getUserRepository()).findAll();
  }
}
