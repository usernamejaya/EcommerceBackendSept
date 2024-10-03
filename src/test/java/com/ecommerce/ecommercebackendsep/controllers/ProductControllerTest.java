package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.models.Inventory;
import com.ecommerce.ecommercebackendsep.models.Product;
import com.ecommerce.ecommercebackendsep.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById_Success() throws Exception {
        Product product = new Product();
        product.setId(1L);
        Optional<Product> optionalProduct = Optional.of(product);

        when(productService.getProductById(1L)).thenReturn(optionalProduct);

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testGetProductByName_Success() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getProductByName("test")).thenReturn(products);

        mockMvc.perform(get("/product/search?name=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(productService, times(1)).getProductByName("test");
    }

    @Test
    public void testGetProductByName_NotFound() throws Exception {
        when(productService.getProductByName("test")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/product/search?name=test"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductByName("test");
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("New Product");
        product.setDescription("New product description");
        product.setPrice(99.99);
        Inventory inventory = new Inventory();
        inventory.setInStockQuantity(100);
        product.setInventory(inventory);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\",\"description\":\"New product description\",\"price\":99.99,\"inventory\":{\"inStockQuantity\":100}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Product"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }


    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Product");

        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));

        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1L);
    }
}