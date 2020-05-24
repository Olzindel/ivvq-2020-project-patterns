package patterns.backend.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.User;
import patterns.backend.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceTest {

  private UserService userService;

  @MockBean private UserRepository userRepository;

  @MockBean private MerchantService merchantService;

  @MockBean private OrderService orderService;

  private User user;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();

    userService = new UserService();
    userService.setMerchantService(merchantService);
    userService.setOrderService(orderService);
    userService.setUserRepository(userRepository);

    user = dataLoader.getUser();
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
