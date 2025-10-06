
package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long prodId) {
        Optional<Product> product = productService.getProductById(prodId);
        return ResponseEntity.of(product);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product savedProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @DeleteMapping("/{prodId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long prodId) {
        productService.deleteProduct(prodId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{prodId}/reduce-quantity")
    public ResponseEntity<String> reduceQuantity(@PathVariable Long prodId,
                                                 @RequestParam Integer quantity) {
        productService.reduceProductQuantity(prodId, quantity);
        return ResponseEntity.ok("Quantity reduced successfully.");
    }

    @GetMapping("/filter")
    public List<Product> getProductByPriceLessThan(@RequestParam Double price) {
        return productService.getProductByPriceLessThan(price);
    }

    @GetMapping("/filtername")
    public List<Product> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }
}
