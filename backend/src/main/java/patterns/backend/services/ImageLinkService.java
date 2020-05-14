package patterns.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.ImageLink;
import patterns.backend.exception.ImageLinkNotFoundException;
import patterns.backend.repositories.ImageLinkRepository;

@Service
@Getter
@Setter
public class ImageLinkService {
  @Autowired private ImageLinkRepository imageLinkRepository;

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

  public ImageLink update(final ImageLink imageLink) {
    ImageLink savedImageLink;
    if (imageLink != null) {
      savedImageLink = imageLinkRepository.save(imageLink);
    } else {
      throw new IllegalArgumentException();
    }
    return savedImageLink;
  }

  public void deleteImageLinkById(final Long id) {
    ImageLink imageLink = findImageLinkById(id);
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
}
