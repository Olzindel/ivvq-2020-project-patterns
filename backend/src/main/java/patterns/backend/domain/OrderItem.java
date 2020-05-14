package patterns.backend.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "order_items")
public class OrderItem {
  @Id @GeneratedValue public Long id;

  @Min(0)
  private int quantity;

  @OneToOne(
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private Product product;

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private Order order;

  public OrderItem(int quantity, Product product, Order order) {
    this.quantity = quantity;
    this.product = product;
    this.order = order;
  }
}
