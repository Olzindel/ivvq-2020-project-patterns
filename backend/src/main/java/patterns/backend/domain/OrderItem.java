package patterns.backend.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "order_items")
public class OrderItem implements Serializable {

  @Id @GeneratedValue private Long id;

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
