package patterns.backend.graphql.input;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.OrderStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class OrderInput {
  OrderStatus status;
  List<Long> orderItemIds;
  Long userId;
}
