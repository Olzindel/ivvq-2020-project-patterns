package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
  @Id @GeneratedValue private Long id;

  @PastOrPresent
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate createdAt;

  @NotNull private OrderStatus orderStatus;

  @OneToMany(
    mappedBy = "order",
    fetch = FetchType.EAGER,
    cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
  )
  @JsonIgnore
  private List<OrderItem> orderItems = new ArrayList<>();

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private User user;

  public Order(LocalDate createdAt, OrderStatus orderStatus, User user) {
    this.createdAt = createdAt;
    this.user = user;
    this.orderStatus = orderStatus;
  }
}
