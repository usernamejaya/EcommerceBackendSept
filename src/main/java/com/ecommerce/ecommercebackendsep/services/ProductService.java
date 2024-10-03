package com.ecommerce.ecommercebackendsep.services;

import com.ecommerce.ecommercebackendsep.Exceptions.ResourceNotFoundException;
import com.ecommerce.ecommercebackendsep.models.Product;
import com.ecommerce.ecommercebackendsep.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct;
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);

    }

    public Product createProduct(Product product) {
        Product savedProduct= productRepository.save(product);
        return savedProduct;
    }

    public Product updateProduct(Long productId, Product productDetails) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        product.setDescription(productDetails.getDescription());
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setInventory(productDetails.getInventory());

        return productRepository.save(product); // Save the updated product
    }

    public void deleteProduct(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        productRepository.delete(product); // Delete the product
    }
}
