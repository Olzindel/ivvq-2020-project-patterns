package patterns.backend.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import patterns.backend.DataLoader;
import patterns.backend.domain.User;
import patterns.backend.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserDetailsServiceImplTest {

  @MockBean private UserRepository userRepository;

  private UserDetailsServiceImpl userDetailsService;

  private User user;
  private String token;

  @BeforeEach
  void setUp() {
    DataLoader dataLoader = new DataLoader();

    userDetailsService = new UserDetailsServiceImpl();
    userDetailsService.setUserRepository(userRepository);

    user = dataLoader.getUser();
    token = dataLoader.getToken();
  }

  @Test
  void loadUserByUsername() {
    // given: an user and an UserDetailsService
    when(userDetailsService.getUserRepository().findByUsername("user")).thenReturn(user);
    // when: the loadUserByUsername method is invoked
    userDetailsService.loadUserByUsername("user");
    // then: the findByUsername method of the Repository is invoked
    verify(userDetailsService.getUserRepository()).findByUsername("user");
  }

  @Test
  void loadUserByUsernameThrowExceptionUserNull() {
    // given: an UserDetailsService
    when(userDetailsService.getUserRepository().findByUsername("user")).thenReturn(null);
    // when: the loadUserByUsername method is invoked with unknown username
    try {
      userDetailsService.loadUserByUsername("user");
      // then: UsernameNotFoundException is thrown
      fail("Should have thrown UsernameNotFoundException");
    } catch (UsernameNotFoundException e) {
    }
  }

  @Test
  void getUserFromToken() {
    // given: an user and an UserDetailsService
    when(userDetailsService.getUserRepository().findByUsername("user")).thenReturn(user);
    // when: the getUserFromToken method is invoked
    userDetailsService.getUserFromToken(token);
    // then: the findByUsername method of the Repository is invoked
    verify(userDetailsService.getUserRepository()).findByUsername("user");
  }

  @Test
  void getUserFromTokenThrowExceptionTokenNull() {
    try {
      // when: the getUserFromToken is invoked with null token
      userDetailsService.getUserFromToken(null);
      // then: IllegalArgumentException is thrown
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
    }
  }
}
