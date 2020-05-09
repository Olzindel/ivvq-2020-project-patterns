package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.ImageLink;

public interface ImageLinkRepository extends CrudRepository<ImageLink, Long> {
}
