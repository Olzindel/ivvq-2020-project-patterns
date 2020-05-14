package patterns.backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ImageLinks")
public class ImageLink {

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
