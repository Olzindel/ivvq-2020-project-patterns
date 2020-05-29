package patterns.backend.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static patterns.backend.security.SecurityConstants.HEADER_STRING;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import patterns.backend.DataLoader;
import patterns.backend.domain.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class JWTAuthorizationFilterTest {

  private JWTAuthorizationFilter jwtAuthorizationFilter;

  @MockBean private UserDetailsServiceImpl userDetailsService;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private HttpServletRequest request;

  @MockBean private HttpServletResponse response;

  @MockBean private FilterChain chain;

  private User user;
  private String token;

  @BeforeEach
  void setUp() {
    DataLoader dataLoader = new DataLoader();

    jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager, userDetailsService);
    user = dataLoader.getUser();
    token = dataLoader.getToken();
  }

  @Test
  void doFilterInternal() throws IOException, ServletException {
    // given: an HttpServletRequest, HttpServletResponse, FilterChain and a valid token
    when(request.getHeader(HEADER_STRING)).thenReturn("Bearer " + token);
    when(userDetailsService.getUserFromToken("Bearer " + token)).thenReturn(user);
    // when: the doFilterInternal method is invoked
    jwtAuthorizationFilter.doFilterInternal(request, response, chain);
    // then: the doFilter method from the FilterChain is invoked
    verify(chain).doFilter(request, response);
  }

  @Test
  void doFilterInternalHeaderNull() throws IOException, ServletException {
    // given: an HttpServletRequest, HttpServletResponse, FilterChain and null header
    when(request.getHeader(HEADER_STRING)).thenReturn(null);
    // when: the doFilterInternal method is invoked
    jwtAuthorizationFilter.doFilterInternal(request, response, chain);
    // then: the doFilter method from the FilterChain is invoked
    verify(chain).doFilter(request, response);
  }

  @Test
  void getAuthentication() {
    // given: an HttpServletRequest, User and a valid token
    when(request.getHeader(HEADER_STRING)).thenReturn(token);
    when(userDetailsService.getUserFromToken(token)).thenReturn(user);
    // when: the getAuthentication method is invoked
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        jwtAuthorizationFilter.getAuthentication(request);
    // then: the credentials obtained corresponds to the given user
    assertEquals(user.getUsername(), usernamePasswordAuthenticationToken.getCredentials());
  }

  @Test
  void getAuthenticationTokenNull() {
    // given: an HttpServletRequest and a null token
    when(request.getHeader(HEADER_STRING)).thenReturn(null);
    // when: the getAuthentication method is invoked
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        jwtAuthorizationFilter.getAuthentication(request);
    // then: the authentication obtained is null
    assertNull(usernamePasswordAuthenticationToken);
  }
}
