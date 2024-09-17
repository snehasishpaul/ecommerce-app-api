package com.snehasish.ecommerce.repository;

import com.snehasish.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByNameOrDescriptionContaining(String name, String description);
}
