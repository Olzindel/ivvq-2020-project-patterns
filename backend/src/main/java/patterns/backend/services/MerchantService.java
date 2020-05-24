package patterns.backend.services;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.domain.ProductStatus;
import patterns.backend.domain.User;
import patterns.backend.exception.MerchantNotFoundException;
import patterns.backend.graphql.input.MerchantInput;
import patterns.backend.repositories.MerchantRepository;

@Service
@Getter
@Setter
@Transactional
public class MerchantService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

  @Autowired private MerchantRepository merchantRepository;

  @Autowired private UserService userService;

  @Autowired private ProductService productService;

  public Merchant findMerchantById(final Long id) {
    Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
    if (!optionalMerchant.isPresent()) {
      throw new MerchantNotFoundException(id);
    } else {
      return optionalMerchant.get();
    }
  }

  public Merchant create(MerchantInput merchantInput) {
    Merchant merchant = new Merchant(merchantInput.getName(), null);

    if (merchantInput.getUserId() != null) {
      User admin = userService.findUserById(merchantInput.getUserId());
      merchant.setAdmin(admin);
    }

    if (merchantInput.getProductIds() != null && !merchantInput.getProductIds().isEmpty()) {
      Set<Product> products = new HashSet<>();
      for (Long id : merchantInput.getProductIds()) {
        products.add(productService.findProductById(id));
      }
      merchant.setProducts(products);
    }
    return create(merchant);
  }

  public Merchant create(final Merchant merchant) {
    Merchant savedMerchant;
    if (merchant != null) {
      savedMerchant = merchantRepository.save(merchant);
      if (merchant.getAdmin() != null) {
        merchant.getAdmin().addMerchant(merchant);
      }
      if (merchant.getProducts() != null) {
        for (Product product : merchant.getProducts()) {
          product.setMerchant(merchant);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return savedMerchant;
  }

  public void deleteMerchantById(final Long id) {
    Merchant merchant = findMerchantById(id);
    for (Product product : merchant.getProducts()) {
      product.setStock(0);
      product.setStatus(ProductStatus.NOT_AVAILABLE);
      product.setMerchant(null);
    }
    merchant.getAdmin().getMerchants().remove(merchant);
    merchantRepository.delete(merchant);
  }

  public long countMerchant() {
    return merchantRepository.count();
  }

  public List<Merchant> findMerchantByUser(Long adminId) {
    return merchantRepository.findMerchantByAdmin_Id(adminId);
  }

  public List<Merchant> findAll(int count) {
    return StreamSupport.stream(merchantRepository.findAll().spliterator(), false)
        .limit(count)
        .collect(Collectors.toList());
  }

  public Merchant update(Long merchantId, MerchantInput merchantInput) {
    Merchant merchant = findMerchantById(merchantId);
    if (merchantInput.getName() != null) {
      merchant.setName(merchantInput.getName());
    }
    if (merchantInput.getProductIds() != null && !merchantInput.getProductIds().isEmpty()) {
      List<Long> productIds = new ArrayList<>(merchantInput.getProductIds());
      List<Long> toDelete =
          merchant.getProducts().stream().map(Product::getId).collect(Collectors.toList());

      toDelete.removeAll(merchantInput.getProductIds());
      productIds.removeAll(toDelete);
      for (Long idToDelete : toDelete) {
        productService.deleteProductById(idToDelete);
      }
      for (Long idToAdd : productIds) {
        merchant.addProduct(productService.findProductById(idToAdd));
      }
    }

    if (merchantInput.getUserId() != null) {
      merchant.getAdmin().getMerchants().remove(merchant);
      User user = userService.findUserById(merchantInput.getUserId());
      merchant.setAdmin(user);
    }

    return create(merchant);
  }
}
