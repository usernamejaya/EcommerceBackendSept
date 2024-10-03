package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.Exceptions.ResourceNotFoundException;
import com.ecommerce.ecommercebackendsep.models.Inventory;
import com.ecommerce.ecommercebackendsep.models.Product;
import com.ecommerce.ecommercebackendsep.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
       Optional<Product> optionalProduct= productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if product is not found
        }

    }
    @GetMapping("/search")   //need to hit endpoint with search?name=ProductName
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name) {
        List<Product> product= productService.getProductByName(name);

        if (!product.isEmpty()) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if product is not found
        }
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product product1= new Product();
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setName(product.getName());
        Inventory inventory = new Inventory();
        inventory.setInStockQuantity(product.getInventory().getInStockQuantity());

        product1.setInventory(inventory);
        Product savedProduct =productService.createProduct(product1);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.deleteProduct(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }






}
