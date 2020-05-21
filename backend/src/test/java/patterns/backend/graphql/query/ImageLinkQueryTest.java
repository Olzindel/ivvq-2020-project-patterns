package patterns.backend.graphql.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.services.ImageLinkService;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ImageLinkQueryTest {

    @MockBean
    ImageLinkService imageLinkService;

    ImageLinkQuery imageLinkQuery;

    @BeforeEach
    public void setup() {
        imageLinkQuery = new ImageLinkQuery();
        imageLinkQuery.setImageLinkService(imageLinkService);
    }

    @Test
    void getImageLinks() {
        imageLinkQuery.getImageLinks(1);
        verify(imageLinkService).findAll(1);
    }

    @Test
    void getImageLink() {
        imageLinkQuery.getImageLink(0L);
        verify(imageLinkService).findImageLinkById(0L);
    }
}