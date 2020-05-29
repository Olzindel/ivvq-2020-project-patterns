package patterns.backend.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Product;
import patterns.backend.graphql.input.ImageLinkInput;
import patterns.backend.repositories.ImageLinkRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ImageLinkServiceTest {

  private ImageLinkService imageLinkService;

  @MockBean private ImageLinkRepository imageLinkRepository;

  @MockBean private ProductService productService;

  private ImageLink imageLink;
  private Product product;
  private ImageLinkInput imageLinkInput;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    imageLink = dataLoader.getImageLink();
    product = dataLoader.getProduct();
    imageLinkInput = dataLoader.getImageLinkInput();

    imageLinkService = new ImageLinkService();
    imageLinkService.setProductService(productService);
    imageLinkService.setImageLinkRepository(imageLinkRepository);
  }

  @Test
  public void testTypeRepository() {
    // the associated Repository of an ImageLinkService is type of CrudRepository
    assertThat(imageLinkService.getImageLinkRepository(), instanceOf(CrudRepository.class));
  }

  @Test
  void findImageLinkById() {
    // given: an ImageLink and an ImageLinkService
    when(imageLinkService.getImageLinkRepository().findById(0L))
        .thenReturn(java.util.Optional.of(imageLink));
    // when: the findAll method is invoked
    imageLinkService.findImageLinkById(0L);
    // then: the findAll method of the Repository is invoked
    verify(imageLinkService.getImageLinkRepository()).findById(0L);
  }

  @Test
  void saveImageLink() {
    // given: an imageLink and an ImageLinkService
    when(imageLinkService.getImageLinkRepository().save(imageLink)).thenReturn(imageLink);
    // when: saveImageLink is invoked
    imageLinkService.create(imageLink);
    // then: the save method of ImageLinkRepository is invoked
    verify(imageLinkService.getImageLinkRepository()).save(imageLink);
    assert imageLink.getProduct().getImageLinks().contains(imageLink);
  }

  @Test
  void createImageLink() {
    // given: an imageLinkInput and an ImageLinkService
    ImageLinkInput imageLinkInput = new ImageLinkInput(imageLink.getImageLink(), 0L);
    when(imageLinkService.getImageLinkRepository().save(imageLink)).thenReturn(imageLink);
    when(imageLinkService.getProductService().findProductById(0L)).thenReturn(product);
    // when: saveImageLink is invoked
    imageLinkService.create(imageLinkInput);
    // then: the save method of ImageLinkRepository is invoked
    verify(productService).findProductById(0L);
    assert imageLink.getProduct().getImageLinks().contains(imageLink);
  }

  @Test
  void countImageLink() {
    // given: an ImageLinkService
    // when: the count method is invoked
    imageLinkService.countImageLink();
    // then: the count method of the Repository is invoked
    verify(imageLinkService.getImageLinkRepository()).count();
  }

  @Test
  void deleteImageLink() {
    // given: an ImageLink saved and an ImageLinkService
    when(imageLinkService.getImageLinkRepository().findById(0L))
        .thenReturn(java.util.Optional.of(imageLink));
    when(imageLinkService.getImageLinkRepository().save(imageLink)).thenReturn(imageLink);
    imageLinkService.create(imageLink);
    // when: the deleteImageLinkById method is invoked
    imageLinkService.deleteImageLinkById(0L);
    // then: the delete method of the Repository is invoked
    verify(imageLinkService.getImageLinkRepository()).delete(imageLink);
    verify(imageLinkService.getImageLinkRepository()).findById(0L);
    assert !imageLink.getProduct().getImageLinks().contains(imageLink);
  }

  @Test
  void findAll() {
    // given: an ImageLinkService
    // when: the findAll method is invoked
    imageLinkService.findAll(8);
    // then: the findAll method of the Repository is invoked
    verify(imageLinkService.getImageLinkRepository()).findAll();
  }

  @Test
  void update() {
    // given: an ImageLinkInput, an ImageLink and an ImageLinkService
    ImageLinkService imageLinkServiceMock = mock(ImageLinkService.class);
    doCallRealMethod().when(imageLinkServiceMock).update(0L, imageLinkInput);
    when(imageLinkServiceMock.findImageLinkById(0L)).thenReturn(imageLink);
    // when: update method is invoked
    imageLinkServiceMock.update(0L, imageLinkInput);
    // then: create(ImageLink) method is invoked
    verify(imageLinkServiceMock).create(imageLink);
  }
}
