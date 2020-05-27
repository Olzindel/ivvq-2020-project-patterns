package patterns.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.exception.ImageLinkNotFoundException;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.repositories.ImageLinkRepository;

@Service
@Getter
@Setter
@Transactional
public class ImageLinkService {
  @Autowired private ImageLinkRepository imageLinkRepository;

  @Autowired private ProductService productService;

  public ImageLink findImageLinkById(final Long id) {
    Optional<ImageLink> optionalImageLink = imageLinkRepository.findById(id);
    if (!optionalImageLink.isPresent()) {
      throw new ImageLinkNotFoundException(id);
    } else {
      return optionalImageLink.get();
    }
  }

  public ImageLink create(final ImageLink imageLink) {
    ImageLink savedImageLink;
    if (imageLink != null) {
      savedImageLink = imageLinkRepository.save(imageLink);
      if (imageLink.getProduct() != null) {
        imageLink.getProduct().addImageLink(imageLink);
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedImageLink;
  }

  public void deleteImageLinkById(final Long id) {
    ImageLink imageLink = findImageLinkById(id);
    if (imageLink.getProduct() != null
        && imageLink.getProduct().getImageLinks() != null
        && !imageLink.getProduct().getImageLinks().isEmpty()) {
      imageLink.getProduct().getImageLinks().remove(imageLink);
    }
    imageLinkRepository.delete(imageLink);
  }

  public long countImageLink() {
    return imageLinkRepository.count();
  }

  public List<ImageLink> findAll(int count) {
    return StreamSupport.stream(imageLinkRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }

  public ImageLink create(ImageLinkInput imageLinkInput) {
    ImageLink imageLink = new ImageLink(imageLinkInput.getImageLink(), null);

    if (imageLinkInput.getProductId() != null) {
      Product product = productService.findProductById(imageLinkInput.getProductId());
      imageLink.setProduct(product);
    }

    return create(imageLink);
  }

  public ImageLink update(Long imageLinkId, ImageLinkInput imageLinkInput) {
    ImageLink imageLink = findImageLinkById(imageLinkId);
    if (imageLinkInput.getImageLink() != null) {
      imageLink.setImageLink(imageLinkInput.getImageLink());
    }
    if (imageLinkInput.getProductId() != null) {
      imageLink.getProduct().getImageLinks().remove(imageLink);
      Product product = productService.findProductById(imageLinkInput.getProductId());
      imageLink.setProduct(product);
    }

    return create(imageLink);
  }
}
