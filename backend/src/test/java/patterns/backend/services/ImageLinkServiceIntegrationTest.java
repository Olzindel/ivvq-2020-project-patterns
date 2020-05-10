package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.*;
import patterns.backend.exception.ImageLinkNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ImageLinkServiceIntegrationTest {
    @Autowired
    private ImageLinkService imageLinkService;

    private ImageLink imageLink;

    private Order order;

    private Product product;

    private Merchant merchant;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("Nathan", "nathan.roche31@gmail.com", "M", LocalDate.now(), LocalDate.now());
        merchant = new Merchant("Market", LocalDate.now(), user);
        product = new Product("Saber", 100000.0, "Ready", "Description", LocalDate.now(), merchant);
        imageLink = new ImageLink("http://www.lueur.fr", product);
    }

    @Test
    public void testSavedImageLinkHasId() {
        // given: an ImageLink not persisted imageLink
        // then: imageLink has no id
        assertNull(imageLink.getId());
        // when: imageLink is persisted
        imageLinkService.create(imageLink);
        // then: imageLink has an id
        assertNotNull(imageLink.getId());
    }

    @Test()
    public void testSaveImageLinkNull() {
        // when: null is persisted via an ImageLinkService
        // then: an exception IllegalArgumentException is lifted
        assertThrows(IllegalArgumentException.class, () -> imageLinkService.create(null));
    }

    @Test
    public void testFetchedImageLinkIsNotNull() {
        // given: an ImageLink imageLink is persisted
        imageLinkService.create(imageLink);
        // when: we call findImageLinkById with the id of that ImageLink
        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());
        // then: the result is not null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedImageLinkHasGoodId() {
        // given: an ImageLink imageLink is persisted
        imageLinkService.create(imageLink);
        // when: we call findImageLinkById with the id of that ImageLink
        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());
        // then: the ImageLink obtained has the correct id
        assertEquals(imageLink.getId(), fetched.getId());
    }

    @Test
    public void testFetchedImageLinkIsUnchanged() {
        // given: an ImageLink imageLink persisted
        imageLinkService.create(imageLink);
        // when: we call findImageLinkById with the id of that ImageLink
        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());
        // then: All the attributes of the ImageLink obtained has the correct values
        assertEquals(imageLink.getImageLink(), fetched.getImageLink());
        assertEquals(imageLink.getProduct(), fetched.getProduct());
    }

    @Test
    public void testUpdatedImageLinkIsUpdated() {
        // given: an ImageLink imageLink persisted
        imageLinkService.create(imageLink);

        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());
        // when: the email is modified at the "object" level
        fetched.setImageLink("http://www.img.fr");
        // when: the object imageLink is updated in the database
        imageLinkService.update(fetched);
        // when: the object imageLink is re-read in the database
        ImageLink fetchedUpdated = imageLinkService.findImageLinkById(imageLink.getId());
        // then: the email has been successfully updated
        assertEquals(fetched.getImageLink(), fetchedUpdated.getImageLink());
    }

    @Test
    public void testSavedImageLinkIsSaved() {
        long before = imageLinkService.countImageLink();
        // given: is new imageLink
        // when: this USer is persisted
        imageLinkService.create(new ImageLink("http://www.lueur.fr", product));
        // then : the number of ImageLink persisted is increased by 1
        assertEquals(before + 1, imageLinkService.countImageLink());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        // given: an ImageLink imageLink persisted
        imageLinkService.create(imageLink);
        long count = imageLinkService.countImageLink();

        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());
        // when: the email is modified at the "object" level
        fetched.setImageLink("http://www.lueur.fr");
        // when: the object is updated in the database
        imageLinkService.update(fetched);
        // then: a new entry has not been created in the database
        assertEquals(count, imageLinkService.countImageLink());
    }

    @Test
    public void testFindImageLinkWithUnexistingId() {
        // when:  findImageLinkById is called with an id not corresponding to any object in database
        // then: ImageLinkNotFoundException is thrown
        assertThrows(ImageLinkNotFoundException.class, () -> imageLinkService.findImageLinkById(1000L));
    }

    @Test
    public void testDeleteImageLinkWithExistingId() {
        // given: an User user persisted
        imageLinkService.create(imageLink);
        ImageLink fetched = imageLinkService.findImageLinkById(imageLink.getId());

        // when: deleteUserById is called with an id corresponding to an object in database
        imageLinkService.deleteImageLinkById(fetched.getId());
        // then: the user is delete
        assertThrows(ImageLinkNotFoundException.class, () -> imageLinkService.findImageLinkById(fetched.getId()));
    }

    @Test
    public void testDeleteImageLinkWithUnexistingId() {
        // when: deleteUserById is called with an id not corresponding to any object in database
        // then: an exception is thrown
        assertThrows(ImageLinkNotFoundException.class, () -> imageLinkService.deleteImageLinkById(0L));
    }

}