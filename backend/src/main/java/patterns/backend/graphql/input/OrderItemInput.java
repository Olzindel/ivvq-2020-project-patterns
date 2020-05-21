package patterns.backend.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class OrderItemInput {
    Integer quantity;
    Long productId;
    Long orderId;
}
