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
import patterns.backend.graphql.input.ProductInput;
import patterns.backend.repositories.ProductRepository;

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
            savedProduct = productRepository.save(product);
            if (product.getImageLinks() != null) {
                for (ImageLink imageLink : product.getImageLinks()) {
                    imageLink.setProduct(product);
                }
            }
            if (product.getMerchant() != null) {
                product.getMerchant().addProduct(product);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return savedProduct;
    }

    public void deleteProductById(final Long id) {
        Product product = findProductById(id);
        product.getMerchant().removeProduct(product);
        for (ImageLink imageLink : product.getImageLinks()) {
            imageLinkService.deleteImageLinkById(imageLink.getId());
        }
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

    public Product create(ProductInput productInput) {
        Product product = new Product(productInput.getName(), productInput.getPrice(), productInput.getStatus(),
                productInput.getDescription(), productInput.getStock(), null);

        if (productInput.getMerchantId() != null) {
            Merchant merchant = merchantService.findMerchantById(productInput.getMerchantId());
            product.setMerchant(merchant);
        }

        if (productInput.getImageLinkIds() != null && !productInput.getImageLinkIds().isEmpty()) {
            Set<ImageLink> imageLinks = new HashSet<>();
            for (Long id : productInput.getImageLinkIds()) {
                imageLinks.add(imageLinkService.findImageLinkById(id));
            }
            product.setImageLinks(imageLinks);
        }

        return create(product);
    }

    public Product update(long productId, ProductInput productInput) {
        Product product = findProductById(productId);

        if (productInput.getDescription() != null) {
            product.setDescription(productInput.getDescription());
        }
        if (productInput.getName() != null) {
            product.setName(productInput.getName());
        }
        if (productInput.getStatus() != null) {
            product.setStatus(productInput.getStatus());
        }
        if (productInput.getPrice() != null) {
            product.setPrice(productInput.getPrice());
        }
        if (productInput.getStock() != null) {
            product.setStock(productInput.getStock());
        }

        if (productInput.getImageLinkIds() != null && !productInput.getImageLinkIds().isEmpty()) {
            List<Long> toDelete = product.getImageLinks().stream().
                    map(ImageLink::getId).
                    collect(Collectors.toList());

            toDelete.removeAll(productInput.getImageLinkIds());

            for (Long idToAdd : toDelete) {
                imageLinkService.deleteImageLinkById(idToAdd);
            }
        }

        if (productInput.getMerchantId() != null) {
            product.getMerchant().getProducts().remove(product);
            Merchant merchant = merchantService.findMerchantById(productInput.getMerchantId());
            product.setMerchant(merchant);
        }

        return create(product);
    }
}
