package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.Product;
import patterns.backend.exception.ProductNotFoundException;
import patterns.backend.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException(id);
        } else {
            return optionalProduct.get();
        }
    }

    public Product saveProduct(Product product) {

        Product savedProduct;
        if (product != null) {
            savedProduct = productRepository.save(product);
        } else {
            throw new IllegalArgumentException();
        }
        return savedProduct;
    }

    public void deleteProductById(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    public long countProduct() {
        return productRepository.count();
    }

    public List<Product> findAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
