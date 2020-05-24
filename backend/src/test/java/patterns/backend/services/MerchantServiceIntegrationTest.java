package patterns.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.DataLoader;
import patterns.backend.domain.Merchant;
import patterns.backend.exception.MerchantNotFoundException;
import patterns.backend.graphql.input.MerchantInput;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MerchantServiceIntegrationTest {
  @Autowired private MerchantService merchantService;

  private Merchant merchant;

  @BeforeEach
  public void setup() {
    DataLoader dataLoader = new DataLoader();
    merchant = dataLoader.getMerchant();
  }

  @Test
  public void testSavedMerchantHasId() {
    // given: a Merchant not persisted merchant
    // then: merchant has no id
    assertNull(merchant.getId());
    // when: merchant is persisted
    merchantService.create(merchant);
    // then: merchant has an id
    assertNotNull(merchant.getId());
  }

  @Test()
  public void testSaveMerchantNull() {
    // when: null is persisted via a MerchantService
    // then: a exception IllegalArgumentException is lifted
    assertThrows(IllegalArgumentException.class, () -> merchantService.create((Merchant) null));
  }

  @Test
  public void testFetchedMerchantIsNotNull() {
    // given: a Merchant merchant is persisted
    merchantService.create(merchant);
    // when: we call findMerchantById with the id of that Merchant
    Merchant fetched = merchantService.findMerchantById(merchant.getId());
    // then: the result is not null
    assertNotNull(fetched);
  }

  @Test
  public void testFetchedMerchantHasGoodId() {
    // given: a Merchant merchant is persisted
    merchantService.create(merchant);
    // when: we call findMerchantById with the id of that Merchant
    Merchant fetched = merchantService.findMerchantById(merchant.getId());
    // then: the Merchant obtained has the correct id
    assertEquals(merchant.getId(), fetched.getId());
  }

  @Test
  public void testFetchedMerchantIsUnchanged() {
    // given: a Merchant merchant persisted
    merchantService.create(merchant);
    // when: we call findMerchantById with the id of that Merchant
    Merchant fetched = merchantService.findMerchantById(merchant.getId());
    // then: All the attributes of the Merchant obtained has the correct values
    assertEquals(merchant.getName(), fetched.getName());
  }

  @Test
  public void testUpdatedMerchantIsUpdated() {
    // given: a Merchant merchant persisted
    Merchant fetched = merchantService.create(merchant);
    // when: a MerchantInput with a different email
    MerchantInput merchantInput = new MerchantInput("Husbando Market-dess", null, null);
    // when: the object merchant is updated in the database
    merchantService.update(fetched.getId(), merchantInput);
    // when: the object merchant is re-read in the database
    Merchant fetchedUpdated = merchantService.findMerchantById(merchant.getId());
    // then: the email has been successfully updated
    assertEquals(fetched.getName(), fetchedUpdated.getName());
  }

  @Test
  public void testSavedMerchantIsSaved() {
    long before = merchantService.countMerchant();
    // given: is new merchant
    // when: this USer is persisted
    merchantService.create(merchant);
    // then : the number of Merchant persisted is increased by 1
    assertEquals(before + 1, merchantService.countMerchant());
  }

  @Test
  public void testUpdateDoesNotCreateANewEntry() {
    // given: a Merchant merchant persisted
    Merchant fetched = merchantService.create(merchant);
    long count = merchantService.countMerchant();
    // when: a MerchantInput with a different email
    MerchantInput merchantInput = new MerchantInput("Husbando Market-dess", null, null);
    // when: the object is updated in the database
    merchantService.update(fetched.getId(), merchantInput);
    // then: a new entry has not been created in the database
    assertEquals(count, merchantService.countMerchant());
  }

  @Test
  public void testFindMerchantWithUnexistingId() {
    // when:  findMerchantById is called with an id not corresponding to any object in database
    // then: MerchantNotFoundException is thrown
    assertThrows(MerchantNotFoundException.class, () -> merchantService.findMerchantById(1000L));
  }

  @Test
  public void testDeleteMerchantWithExistingId() {
    // given: an User user persisted
    merchantService.create(merchant);
    Merchant fetched = merchantService.findMerchantById(merchant.getId());

    // when: deleteUserById is called with an id corresponding to an object in database
    merchantService.deleteMerchantById(fetched.getId());
    // then: the user is delete
    assertThrows(
        MerchantNotFoundException.class, () -> merchantService.findMerchantById(fetched.getId()));
  }

  @Test
  public void testDeleteMerchantWithUnexistingId() {
    // when: deleteUserById is called with an id not corresponding to any object in database
    // then: an exception is thrown
    assertThrows(MerchantNotFoundException.class, () -> merchantService.deleteMerchantById(0L));
  }
}
