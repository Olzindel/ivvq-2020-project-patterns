package patterns.backend.graphql.input;

import java.util.List;
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
public class MerchantInput {
  String name;
  Long userId;
  List<Long> productIds;
}
