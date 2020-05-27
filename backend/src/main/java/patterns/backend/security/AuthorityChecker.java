package patterns.backend.security;

import org.springframework.stereotype.Component;

@Component("authorityChecker")
public class AuthorityChecker {

  /* public boolean isMerchant(UserDetailsServiceImpl authentication) {
      for (GrantedAuthority authority : authentication.getAuthorities()) {
          User user = (User)authority;
        //  if (user.getMerchant()) {
      //        return true;
    //      }
    //  }
      return false;
  }*/
}
