package patterns.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Order;
import patterns.backend.domain.User;
import patterns.backend.exception.UserNotFoundException;
import patterns.backend.graphql.input.UserInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserServiceIntegrationTest {
  @Autowired private UserService userService;

  private User user;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    user = dataLoader.getUser();
  }

  @Test
  public void testSavedUserHasId() {
    // given: an User not persisted user
    // then: user has no id
    assertNull(user.getId());
    // when: user is persisted
    userService.create(user);
    // then: user has an id
    assertNotNull(user.getId());
  }

  @Test()
  public void testSaveUserNull() {
    // when: null is persisted via an UserService
    // then: an exception IllegalArgumentException is lifted
    assertThrows(IllegalArgumentException.class, () -> userService.create((User) null));
  }

  @Test
  public void testFetchedUserIsNotNull() {
    // given: an User user is persisted
    userService.create(user);
    // when: we call findUserById with the id of that User
    User fetched = userService.findUserById(user.getId());
    // then: the result is not null
    assertNotNull(fetched);
  }

  @Test
  public void testFetchedUserHasGoodId() {
    // given: an User user is persisted
    userService.create(user);
    // when: we call findUserById with the id of that User
    User fetched = userService.findUserById(user.getId());
    // then: the User obtained has the correct id
    assertEquals(user.getId(), fetched.getId());
  }

  @Test
  public void testFetchedUserIsUnchanged() {
    // given: an User user persisted
    userService.create(user);
    // when: we call findUserById with the id of that User
    User fetched = userService.findUserById(user.getId());
    // then: All the attributes of the User obtained has the correct values
    assertEquals(user.getFirstName(), fetched.getFirstName());
    assertEquals(user.getLastName(), fetched.getLastName());
    assertEquals(user.getEmail(), fetched.getEmail());
    assertEquals(user.getGender(), fetched.getGender());
  }

  @Test
  public void testUpdatedUserIsUpdated() {
    // given: an User user persisted
    User fetched = userService.create(user);

    UserInput userInput =
        new UserInput(
            user.getFirstName(),
            user.getLastName(),
            "tyty@tyty.fr",
            user.getGender(),
            user.getStreet(),
            user.getPostalCode(),
            user.getCity(),
            false,
            user.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
    // when: the object user is updated in the database
    userService.update(user.getId(), userInput);
    // when: the object user is re-read in the database
    User fetchedUpdated = userService.findUserById(user.getId());
    // then: the email has been successfully updated
    assertEquals(fetched.getEmail(), fetchedUpdated.getEmail());
  }

  @Test
  public void testSavedUserIsSaved() {
    long before = userService.countUser();
    // given: is new user
    // when: this USer is persisted
    userService.create(user);
    // then : the number of User persisted is increased by 1
    assertEquals(before + 1, userService.countUser());
  }

  @Test
  public void testUpdateDoesNotCreateANewEntry() {

    // given: an User user persisted
    User fetched = userService.create(user);
    long count = userService.countUser();
    UserInput userInput =
        new UserInput(
            user.getFirstName(),
            user.getLastName(),
            "tyty@tyty.fr",
            user.getGender(),
            user.getStreet(),
            user.getPostalCode(),
            user.getCity(),
            false,
            user.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
    // when: the object user is updated in the database
    userService.update(user.getId(), userInput);
    // then: a new entry has not been created in the database
    assertEquals(count, userService.countUser());
  }

  @Test
  public void testFindUserWithUnexistingId() {
    // when:  findUserById is called with an id not corresponding to any object in database
    // then: UserNotFoundException is thrown
    assertThrows(UserNotFoundException.class, () -> userService.findUserById(1000L));
  }

  @Test
  public void testDeleteUserWithExistingId() {
    // given: an User user persisted
    userService.create(user);
    User fetched = userService.findUserById(user.getId());

    // when: deleteUserById is called with an id corresponding to an object in database
    userService.deleteUserById(fetched.getId());
    // then: the user is delete
    assertThrows(UserNotFoundException.class, () -> userService.findUserById(fetched.getId()));
  }

  @Test
  public void testDeleteUserWithUnexistingId() {
    // when: deleteUserById is called with an id not corresponding to any object in database
    // then: an exception is thrown
    assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(0L));
  }
}
