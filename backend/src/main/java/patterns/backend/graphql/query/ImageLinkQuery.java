package patterns.backend.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterns.backend.domain.ImageLink;
import patterns.backend.services.ImageLinkService;

import java.util.List;

@Component
public class ImageLinkQuery implements GraphQLQueryResolver {

    @Autowired
    ImageLinkService imageLinkService;

    public List<ImageLink> getImageLinks(final int count) {
        return imageLinkService.findAll(count);
    }

    public ImageLink getImageLink(final Long imageLinkId) {
        return imageLinkService.findImageLinkById(imageLinkId);
    }

}
