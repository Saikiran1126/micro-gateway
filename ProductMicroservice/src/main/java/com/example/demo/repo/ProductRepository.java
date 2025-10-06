package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
 
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE p.price < :price")
	List<Product> filterByPrice(@Param("price") Double price);
	
	@Query("SELECT p FROM Product p WHERE p.name = :name")
	List<Product> filterByName(@Param("name") String name);
}
