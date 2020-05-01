package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Product;
import patterns.backend.dto.ProductDto;
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
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtos = productService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") final Long productId) {
        ProductDto productDto = convertToDto(productService.findProductById(productId));
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product) {

        ProductDto productDto = convertToDto(productService.create(product));
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product) {
        ProductDto productDto = convertToDto(productService.update(product));
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") final Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);
        return productDTO;
    }

}
