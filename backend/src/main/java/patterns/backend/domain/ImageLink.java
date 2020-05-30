package patterns.backend.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "image_links")
public class ImageLink implements Serializable {

  @Id @GeneratedValue private Long id;

  @NotNull @URL private String imageLink;

  @ManyToOne(
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
  )
  private Product product;

  public ImageLink(String imageLink, Product product) {
    this.imageLink = imageLink;
    this.product = product;
  }
}
