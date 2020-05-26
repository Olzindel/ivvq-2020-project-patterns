package patterns.backend.graphql.mutation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.services.ImageLinkService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ImageLinkMutationTest {

  @MockBean ImageLinkService imageLinkService;

  ImageLinkMutation imageLinkMutation;
  ImageLinkInput imageLinkInput;

  @BeforeEach
  public void setup() {
    imageLinkMutation = new ImageLinkMutation();
    imageLinkMutation.setImageLinkService(imageLinkService);
    imageLinkInput = new ImageLinkInput();
  }

  @Test
  void createImageLink() {
    imageLinkMutation.createImageLink(imageLinkInput);
    verify(imageLinkService).create(imageLinkInput);
  }

  @Test
  void deleteImageLink() {
    // given: an ImageLinkMutation and an id
    // when: deleteImageLink is invoked
    imageLinkMutation.deleteImageLink(0L);

    // then:
    verify(imageLinkService).deleteImageLinkById(0L);
  }

  @Test
  void updateImageLink() {
    imageLinkMutation.updateImageLink(0L, imageLinkInput);
    verify(imageLinkService).update(0L, imageLinkInput);
  }
}
