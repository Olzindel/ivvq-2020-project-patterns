package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "merchants")
public class Merchant {

  @Id @GeneratedValue private Long id;

  @NotNull @NotEmpty private String name;

  @PastOrPresent
  @NotNull
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate createdAt;

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH}
  )
  @NotNull
  @Valid
  private User admin;

  @OneToMany(
    mappedBy = "merchant",
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private List<Product> products;

  public Merchant(String name, LocalDate createdAt, User admin) {
    this.name = name;
    this.createdAt = createdAt;
    this.admin = admin;
    this.products = new ArrayList<>();
  }

  public void addProduct(Product product) {
    if (!products.contains(product)) products.add(product);
  }
}
