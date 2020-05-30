package patterns.backend.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.User;
import patterns.backend.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserDetailsServiceImplIntegrationTest {
  @Autowired private UserDetailsServiceImpl userDetailsService;

  @Autowired private UserService userService;

  private User user;
  private String token;
  private String unknownToken;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    user = dataLoader.getUser();
    token = dataLoader.getToken();
    unknownToken = dataLoader.getUnknownToken();
  }

  @Test
  public void testLoadUserWithUnknownUsername() {
    // when: loadUserByUsername is called with username not persisted
    // then: an UsernameNotFoundException is thrown
    assertThrows(
        UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("unknown"));
  }

  @Test
  public void testLoadedUserNotNull() {
    // given: an User
    userService.create(user);
    // when: loadUserByUsername is called with its username
    org.springframework.security.core.userdetails.User loadedUser =
        (org.springframework.security.core.userdetails.User)
            userDetailsService.loadUserByUsername(user.getUsername());
    // then: the user loaded is not null
    assertNotNull(loadedUser);
  }

  @Test
  public void testGetUserFromValidToken() {
    // given: an User
    userService.create(user);
    // when: getUserFromToken is invoked with corresponding token
    User userFromToken = userDetailsService.getUserFromToken(token);
    // then: the user obtained is the same than the user given
    assertEquals(user, userFromToken);
  }

  @Test
  public void testGetUserFromNullToken() {
    // when: getUserFromToken is invoked with null token
    // then: an IllegalArgumentException is thrown
    assertThrows(IllegalArgumentException.class, () -> userDetailsService.getUserFromToken(null));
  }

  @Test
  public void testGetUserFromUnknownToken() {
    // when: getUserFromToken is invoked with token not corresponding to any user persisted
    // then: an IllegalArgumentException is thrown
    assertThrows(
        IllegalArgumentException.class, () -> userDetailsService.getUserFromToken(unknownToken));
  }
}
