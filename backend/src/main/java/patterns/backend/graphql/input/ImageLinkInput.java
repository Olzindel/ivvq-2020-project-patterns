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
public class ImageLinkInput {
  String imageLink;
  Long productId;
}
