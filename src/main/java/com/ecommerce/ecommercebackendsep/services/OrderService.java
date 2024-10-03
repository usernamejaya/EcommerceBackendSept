package com.ecommerce.ecommercebackendsep.services;

import com.ecommerce.ecommercebackendsep.Exceptions.OrderNotFoundException;
import com.ecommerce.ecommercebackendsep.models.Order;
import com.ecommerce.ecommercebackendsep.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id){
       Optional<Order> optionalOrder= orderRepository.findById(id);
       return optionalOrder;
    }

    public Order updateOrder(Long id, Order order) throws OrderNotFoundException {
        Optional<Order> optionalOrder= orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("order not found");
        }
        Order order1 = optionalOrder.get();
        order1.setId(order.getId());
        order1.setUser(order.getUser());
        order1.setOrderQuantities(order.getOrderQuantities());
        Order savedOrder = orderRepository.save(order1);
        return savedOrder;

    }

    public void deleteOrder(Long id) throws OrderNotFoundException {
        Optional<Order> optionalOrder= orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("order not found");
        }
        orderRepository.delete(optionalOrder.get());
        return;
    }
}
