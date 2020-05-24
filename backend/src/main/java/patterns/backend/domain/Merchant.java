package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "merchants")
public class Merchant {

  @Id @GeneratedValue private Long id;

  @NotNull @NotEmpty private String name;

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH}
  )
  @Valid
  private User admin;

  @JsonIgnore
  @OneToMany(
    mappedBy = "merchant",
    fetch = FetchType.EAGER,
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private Set<Product> products = new HashSet<>();

  public Merchant(String name, User admin) {
    this.name = name;
    this.admin = admin;
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }
}
