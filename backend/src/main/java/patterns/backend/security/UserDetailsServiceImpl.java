package patterns.backend.security;

import static patterns.backend.security.SecurityConstants.SECRET;
import static patterns.backend.security.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.nio.charset.Charset;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import patterns.backend.repositories.UserRepository;

@Getter
@Setter
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    patterns.backend.domain.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
  }

  public patterns.backend.domain.User getUserFromToken(String token) {
    if (token != null) {
      patterns.backend.domain.User user = null;
      String username =
          JWT.require(Algorithm.HMAC512(SECRET.getBytes(Charset.forName("UTF-8"))))
              .build()
              .verify(token.replace(TOKEN_PREFIX, ""))
              .getSubject();
      if (username != null && (user = userRepository.findByUsername(username)) != null) {
        return user;
      }
      throw new IllegalArgumentException();
    }
    throw new IllegalArgumentException();
  }
}
