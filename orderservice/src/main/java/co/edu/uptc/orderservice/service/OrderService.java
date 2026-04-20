package co.edu.uptc.orderservice.service;

import co.edu.uptc.orderservice.model.Order;
import co.edu.uptc.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        Order saved = orderRepository.save(order);
        System.out.println("[ORDER SERVICE] Orden creada en BD: " + saved);
        return saved;
    }

    public void updateStatus(Long orderId, String newStatus) {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
            System.out.println("[ORDER SERVICE] Orden " + orderId + " -> estado: " + newStatus);
        } else {
            System.out.println("[ORDER SERVICE] WARN: no se encontró orden con id=" + orderId);
        }
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}