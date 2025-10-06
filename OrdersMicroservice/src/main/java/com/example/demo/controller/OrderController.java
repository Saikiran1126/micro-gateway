package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    // ✅ Optional default GET to avoid 404 when hitting /orders
    @GetMapping
    public ResponseEntity<String> defaultMessage() {
        return ResponseEntity.ok("Orders microservice is running.");
    }

    // Get all products from ProductMicroservice
    @GetMapping("/product-info")
    public ResponseEntity<String> getProductInfo() {
        try {
            String productServiceUrl = "http://product-service/products";
            String response = restTemplate.getForObject(productServiceUrl, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch product info: " + e.getMessage());
        }
    }

    // Place order and reduce product quantity
    @PostMapping("/place/{productId}")
    public ResponseEntity<String> placeOrder(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        try {
            if (quantity <= 0) {
                return ResponseEntity.badRequest().body("Quantity must be greater than zero.");
            }

            // Call ProductMicroservice to reduce quantity
            String reduceQuantityUrl = String.format(
                    "http://product-service/products/%d/reduce-quantity?quantity=%d",
                    productId,
                    quantity
            );

            restTemplate.put(reduceQuantityUrl, null);

            return ResponseEntity.ok("Order placed successfully, quantity reduced.");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to reduce product quantity: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
