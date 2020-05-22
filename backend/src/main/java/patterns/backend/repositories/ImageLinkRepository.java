package patterns.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import patterns.backend.domain.ImageLink;

@Repository
public interface ImageLinkRepository extends CrudRepository<ImageLink, Long> {
}
