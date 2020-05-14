package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.exception.ImageLinkNotFoundException;
import patterns.backend.repositories.ImageLinkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
@Transactional
public class ImageLinkService {
    @Autowired
    private ImageLinkRepository imageLinkRepository;

    @Autowired
    private ProductService productService;

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
        imageLink.getProduct().getImageLinks().remove(imageLink);
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

    public ImageLink create(ImageLink imageLink, Long productId) {
        Product product = productService.findProductById(productId);
        imageLink.setProduct(product);
        return create(imageLink);
    }
}
