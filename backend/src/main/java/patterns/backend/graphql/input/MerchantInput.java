package patterns.backend.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
