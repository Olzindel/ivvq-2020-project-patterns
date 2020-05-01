package patterns.backend.controller;

import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Product;
import patterns.backend.dto.ProductDTO;
import patterns.backend.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
public class ProductController {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<ProductDTO> getProducts() {
        List<Product> users = productService.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public void getProduct(@PathVariable("id") final Long id) {
        productService.findProductById(id);
    }

    @PutMapping()
    public void addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@ApiParam(value = "ID of person to return", required = true, example = "123") @PathVariable("id") final Long id) {
        productService.deleteProductById(id);
    }

    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

}
