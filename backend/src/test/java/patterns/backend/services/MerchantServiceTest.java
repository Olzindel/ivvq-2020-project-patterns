package patterns.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import patterns.backend.DataLoader;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.domain.User;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.repositories.MerchantRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MerchantServiceTest {

    private MerchantService merchantService;

    @MockBean
    private MerchantRepository merchantRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    private Merchant merchant;

    @BeforeEach
    public void setup() {
        DataLoader dataLoader = new DataLoader();
        merchant = dataLoader.getMerchant();

        merchantService = new MerchantService();
        merchantService.setMerchantRepository(merchantRepository);
        merchantService.setProductService(productService);
        merchantService.setUserService(userService);
    }


    @Test
    public void testTypeRepository() {
        // the associated Repository of a MerchantService is type of CrudRepository
        assertThat(merchantService.getMerchantRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    void findMerchantById() {
        // given: a Merchant and a MerchantService
        when(merchantService.getMerchantRepository().findById(0L)).thenReturn(java.util.Optional.of(merchant));
        // when: the findAll method is invoked
        merchantService.findMerchantById(0L);
        // then: the findAll method of the Repository is invoked
        verify(merchantService.getMerchantRepository()).findById(0L);
    }

    @Test
    void saveMerchant() {
        // given: a merchant and an merchantService
        when(merchantService.getMerchantRepository().save(merchant)).thenReturn(merchant);
        // when: saveMerchant is invoked
        merchantService.create(merchant);
        // then: the save method of MerchantRepository is invoked
        verify(merchantService.getMerchantRepository()).save(merchant);
    }

    @Test
    void createMerchant() {
        // given: a merchant, merchantInput and merchantService
        User admin = merchant.getAdmin();
        admin.setId(0L);
        HashSet<Product> products = (HashSet<Product>) merchant.getProducts();
        List<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toList());
        MerchantInput merchantInput = new MerchantInput(merchant.getName(), admin.getId(), productIds);
        Merchant merchantToReturn = new Merchant(merchant.getName(), merchant.getAdmin());
        merchantToReturn.setProducts(products);
        when(merchantRepository.save(Mockito.any())).thenReturn(merchantToReturn);
        when(userService.findUserById(admin.getId())).thenReturn(admin);
        when(productService.findProductById(productIds.get(0))).thenReturn(products.stream().findFirst().get());

        merchant.setAdmin(null);
        merchant.setProducts(new HashSet<>());
        // when: saveMerchant is invoked
        Merchant merchantFetch = merchantService.create(merchantInput);
        // then: the save method of MerchantRepository is invoked
        assert merchantFetch.getAdmin().equals(admin);
        assert merchantFetch.getProducts().stream().allMatch(product -> products.contains(product));
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
        Set<Product> products = merchant.getProducts();
        when(merchantService.getMerchantRepository().findById(0L)).thenReturn(java.util.Optional.of(merchant));
        // when: the deleteMerchantById method is invoked
        merchantService.deleteMerchantById(0L);
        // then: the delete method of the Repository is invoked
        verify(merchantService.getMerchantRepository()).delete(merchant);
        for (Product product : products) {
            assert product.getStock() == 0;
            assert product.getStatus().equals(ProductStatus.NOT_AVAILABLE);
            assert product.getMerchant() == null;
        }
        assert !merchant.getAdmin().getMerchants().contains(merchant);
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