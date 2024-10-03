package com.ecommerce.ecommercebackendsep.repositories;

import com.ecommerce.ecommercebackendsep.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    Optional<Product> findById(Long Id);
}
