package patterns.backend.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static patterns.backend.security.SecurityConstants.*;
import static patterns.backend.security.SecurityConstants.SECRET;

import com.auth0.jwt.JWT;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class JWTAuthenticationFilterTest {

  private JWTAuthenticationFilter jwtAuthenticationFilter;

  @MockBean private HttpServletRequest request;

  @MockBean private HttpServletResponse response;

  @MockBean private FilterChain chain;

  @MockBean private Authentication authentication;

  @MockBean private AuthenticationManager authenticationManager;

  @BeforeEach
  void setUp() {
    jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager);
  }

  @Test
  void successfulAuthentication() throws IOException, ServletException {
    // given: an HttpServletRequest, HttpServletResponse, FilterChain, Authentication and
    // UserDetails
    when(((User) authentication.getPrincipal())).thenReturn(mock(User.class));
    when(((User) authentication.getPrincipal()).getUsername()).thenReturn("user");
    // when: the successfulAuthentication method is invoked
    jwtAuthenticationFilter.successfulAuthentication(request, response, chain, authentication);
    String token =
        JWT.create()
            .withSubject(((User) authentication.getPrincipal()).getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(HMAC512(SECRET.getBytes(Charset.forName("UTF-8"))));
    // then: the addHeader method from the HttpServletResponse is invoked
    assertFalse(token.isEmpty());
  }
}
