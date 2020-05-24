package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "users")
public class User {

  @Id @GeneratedValue private Long id;

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  @Email @NotNull private String email;

  @Pattern(regexp = "[MF]")
  @NotNull
  private String gender;

  private String street;

  @Pattern(regexp = "^(([0-8][0-9])|(9[0-5]))[0-9]{3}$")
  private String postalCode;

  private String city;

  @NotNull private Boolean merchant;

  @JsonIgnore
  @OneToMany(
    mappedBy = "user",
    fetch = FetchType.EAGER,
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private Set<Order> orders = new HashSet<>();

  public User(
      String username,
      String password,
      String firstName,
      String lastName,
      String email,
      String gender,
      String street,
      String postalCode,
      String city,
      Boolean merchant) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.gender = gender;
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.merchant = merchant;
  }

  public void addOrder(Order order) {
    orders.add(order);
  }
}
