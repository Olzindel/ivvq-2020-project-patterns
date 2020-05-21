package patterns.backend.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.services.ImageLinkService;

@Component
@Transactional
@Getter
@Setter
public class ImageLinkMutation implements GraphQLMutationResolver {

    @Autowired
    private ImageLinkService imageLinkService;

    public ImageLink createImageLink(ImageLinkInput imageLinkInput) {
        return imageLinkService.create(imageLinkInput);
    }

    public Long deleteImageLink(Long imageLinkId) {
        imageLinkService.deleteImageLinkById(imageLinkId);
        return imageLinkId;
    }

    public ImageLink updateImageLink(Long imageLinkId, ImageLinkInput imageLinkInput) {
        return imageLinkService.update(imageLinkId, imageLinkInput);
    }

}
