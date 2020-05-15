package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.services.ImageLinkService;
import patterns.backend.services.ProductService;

@Component
@Transactional
public class ImageLinkMutation implements GraphQLMutationResolver {

    @Autowired
    private ImageLinkService imageLinkService;

    @Autowired
    private ProductService productService;

    public ImageLink createImageLink(String imageLink, Long productId) {
        ImageLink il = new ImageLink(imageLink, null);
        return imageLinkService.create(il, productId);
    }

    public Long deleteImageLink(Long imageLinkId) {
        imageLinkService.deleteImageLinkById(imageLinkId);
        return imageLinkId;
    }

    public ImageLink updateImageLink(Long imageLinkId, String imageLinkString, Long productId) {
        ImageLink imageLink = imageLinkService.findImageLinkById(imageLinkId);
        if (productId != null) {
            imageLink.getProduct().getImageLinks().remove(imageLink);
            Product product = productService.findProductById(productId);
            imageLink.setProduct(product);
        }

        if (imageLink != null) {
            imageLink.setImageLink(imageLinkString);
        }

        return imageLinkService.update(imageLink);
    }

}
