package com.ecommerce.ecommercebackendsep.controllers;

import com.ecommerce.ecommercebackendsep.Exceptions.OrderNotFoundException;
import com.ecommerce.ecommercebackendsep.models.Order;
import com.ecommerce.ecommercebackendsep.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order createdOrder = orderService.addOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) throws OrderNotFoundException {
        Optional<Order> order = orderService.getOrderById(id);
        if(order.isEmpty()){
            throw new OrderNotFoundException("order not found");
        }
        return ResponseEntity.ok().body(order.get());
    }

    @PutMapping(value = "/updateOrder/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) throws OrderNotFoundException {
        Order order1= orderService.updateOrder(id,order);
        return ResponseEntity.ok().body(order1);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Map<String,Boolean>> deleteOrder(@PathVariable long id) throws OrderNotFoundException {
        orderService.deleteOrder(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
        }

}
