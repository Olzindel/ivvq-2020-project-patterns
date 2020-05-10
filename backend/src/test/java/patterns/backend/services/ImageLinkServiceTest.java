package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.User;
import patterns.backend.repositories.ImageLinkRepository;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageLinkServiceTest {

    private ImageLinkService imageLinkService;

    @MockBean
    private ImageLinkRepository imageLinkRepository;

    @MockBean
    private ImageLink imageLink;

    @BeforeEach
    public void setup() {
        imageLinkService = new ImageLinkService();
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
        ImageLink imageLink = new ImageLink();
        when(imageLinkService.getImageLinkRepository().findById(0L)).thenReturn(java.util.Optional.of(imageLink));
        // when: the findAll method is invoked
        imageLinkService.findImageLinkById(0L);
        // then: the findAll method of the Repository is invoked
        verify(imageLinkService.getImageLinkRepository()).findById(0L);
    }

    @Test
    void saveImageLink() {
        // given: an imageLink and an ImageLinkService
        User user = new User("Nathan", "Roche", "nathan.roche31@gmail.com", "M", LocalDate.now(), "8 chemin du", "31000", "Toulouse", LocalDate.now());
        Merchant merchant = new Merchant("Waifu market-dess", LocalDate.now(), user);
        Product product = new Product("Saber", 1.0, "status", "description", 2, LocalDate.now(), merchant);
        ImageLink imageLink = new ImageLink("htt://www.lueur.fr", product);
        imageLink.setProduct(product);
        when(imageLinkService.getImageLinkRepository().save(imageLink)).thenReturn(imageLink);

        // when: saveImageLink is invoked
        imageLinkService.create(imageLink);

        // then: the save method of ImageLinkRepository is invoked
        verify(imageLinkService.getImageLinkRepository()).save(imageLink);
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
        // given: an ImageLink and an ImageLinkService
        ImageLink imageLink = new ImageLink();
        when(imageLinkService.getImageLinkRepository().findById(0L)).thenReturn(java.util.Optional.of(imageLink));
        // when: the deleteImageLinkById method is invoked
        imageLinkService.deleteImageLinkById(0L);
        // then: the delete method of the Repository is invoked
        verify(imageLinkService.getImageLinkRepository()).delete(imageLink);
    }

    @Test
    void findAll() {
        // given: an ImageLinkService
        // when: the findAll method is invoked
        imageLinkService.findAll(8);
        // then: the findAll method of the Repository is invoked
        verify(imageLinkService.getImageLinkRepository()).findAll();
    }
}