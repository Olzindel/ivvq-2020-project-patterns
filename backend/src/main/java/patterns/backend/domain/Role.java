package patterns.backend.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
  USER("USER"),
  MERCHANT("MERCHANT");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return "ROLE_" + this.name;
  }
}
