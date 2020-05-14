package patterns.backend.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.User;
import patterns.backend.repositories.MerchantRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MerchantServiceTest {

  private MerchantService merchantService;

  @MockBean private MerchantRepository merchantRepository;

  @MockBean private Merchant merchant;

  @BeforeEach
  public void setup() {
    merchantService = new MerchantService();
    merchantService.setMerchantRepository(merchantRepository);
  }

  @Test
  public void testTypeRepository() {
    // the associated Repository of a MerchantService is type of CrudRepository
    assertThat(merchantService.getMerchantRepository(), instanceOf(CrudRepository.class));
  }

  @Test
  void findMerchantById() {
    // given: a Merchant and a MerchantService
    Merchant merchant = new Merchant();
    when(merchantService.getMerchantRepository().findById(0L))
        .thenReturn(java.util.Optional.of(merchant));
    // when: the findAll method is invoked
    merchantService.findMerchantById(0L);
    // then: the findAll method of the Repository is invoked
    verify(merchantService.getMerchantRepository()).findById(0L);
  }

  @Test
  void saveMerchant() {
    // given: a merchant and an merchantService
    User user =
        new User("Nathan", "nathan.roche31@gmail.com", "M", LocalDate.now(), LocalDate.now());
    Merchant merchant = new Merchant();
    merchant.setAdmin(user);
    when(merchantService.getMerchantRepository().save(merchant)).thenReturn(merchant);

    // when: saveMerchant is invoked
    merchantService.create(merchant);

    // then: the save method of MerchantRepository is invoked
    verify(merchantService.getMerchantRepository()).save(merchant);
  }

  @Test
  void countMerchant() {
    // given: a MerchantService
    // when: the count method is invoked
    merchantService.countMerchant();
    // then: the count method of the Repository is invoked
    verify(merchantService.getMerchantRepository()).count();
  }

  @Test
  void deleteMerchant() {
    // given: a Merchant and a MerchantService
    Merchant merchant = new Merchant();
    when(merchantService.getMerchantRepository().findById(0L))
        .thenReturn(java.util.Optional.of(merchant));
    // when: the deleteMerchantById method is invoked
    merchantService.deleteMerchantById(0L);
    // then: the delete method of the Repository is invoked
    verify(merchantService.getMerchantRepository()).delete(merchant);
  }

  @Test
  void findAll() {
    // given: a MerchantService
    // when: the findAll method is invoked
    merchantService.findAll(8);
    // then: the findAll method of the Repository is invoked
    verify(merchantService.getMerchantRepository()).findAll();
  }
}
