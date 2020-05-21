package patterns.backend.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ProductStatus;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class ProductInput {
    String name;
    Float price;
    ProductStatus status;
    String description;
    Integer stock;
    List<Long> imageLinkIds;
    Long merchantId;
}
