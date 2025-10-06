
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(ProductDTO productDTO) {
        if (productDTO.getPrice() < 0 || productDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Price and quantity must be non-negative");
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void reduceProductQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity for product id: " + id);
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    public List<Product> getProductByPriceLessThan(Double price) {
        return productRepository.filterByPrice(price);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.filterByName(name);
    }
}
