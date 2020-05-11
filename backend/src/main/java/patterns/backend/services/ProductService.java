package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.domain.ImageLink;
import patterns.backend.domain.Merchant;
import patterns.backend.domain.Product;
import patterns.backend.exception.ProductNotFoundException;
import patterns.backend.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageLinkService imageLinkService;

    @Autowired
    private MerchantService merchantService;

    public Product findProductById(final Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException(id);
        } else {
            return optionalProduct.get();
        }
    }

    public Product create(final Product product) {
        Product savedProduct;
        if (product != null) {
            product.setCreatedAt(LocalDate.now());
            savedProduct = productRepository.save(product);
            if (product.getMerchant() != null) {
                product.getMerchant().addProduct(product);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return savedProduct;
    }

    public Product update(final Product product) {
        Product savedProduct;
        if (product != null) {
            savedProduct = productRepository.save(product);
        } else {
            throw new IllegalArgumentException();
        }
        return savedProduct;
    }

    public void deleteProductById(final Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    public long countProduct() {
        return productRepository.count();
    }

    public List<Product> findAll(int count) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .limit(count)
                .collect(Collectors.toList());
    }

    public Product create(Product product, List<Long> imageLinkIds, Long merchantId) {
        Merchant merchant = merchantService.findMerchantById(merchantId);
        product.setMerchant(merchant);
        Set<ImageLink> imageLinks = new HashSet<>();
        if (imageLinkIds != null) {
            for (Long id : imageLinkIds) {
                imageLinks.add(imageLinkService.findImageLinkById(id));
            }
        }
        product.setImageLinks(imageLinks);
        return create(product);
    }
}
