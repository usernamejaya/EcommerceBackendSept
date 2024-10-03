package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.models.Order;
import com.ecommerce.ecommercebackendsep.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);

        when(orderService.addOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(orderService, times(1)).addOrder(any(Order.class));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(orderService, times(1)).getAllOrders();
    }


    @Test
    public void testGetOrderById_Success() throws Exception {
        Order order = new Order();
        order.setId(1L);
        Optional<Order> optionalOrder = Optional.of(order);

        when(orderService.getOrderById(1L)).thenReturn(optionalOrder);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));

        verify(orderService, times(1)).deleteOrder(1L);
    }
}