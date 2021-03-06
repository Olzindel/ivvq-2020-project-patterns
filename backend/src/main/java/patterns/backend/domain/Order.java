package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "orders")
public class Order implements Serializable {

  @Id @GeneratedValue private Long id;

  @NotNull private OrderStatus status;

  @OneToMany(
    mappedBy = "order",
    fetch = FetchType.EAGER,
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  @JsonIgnore
  private Set<OrderItem> orderItems = new HashSet<>();

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private User user;

  public Order(OrderStatus status, User user) {
    this.user = user;
    this.status = status;
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
  }

  public void removeOrderItem(OrderItem orderItem) {
    orderItems.remove(orderItem);
  }
}
