package co.edu.uptc.orderservice.controller;

import co.edu.uptc.orderservice.model.Order;
import co.edu.uptc.orderservice.service.OrderEventProducer;
import co.edu.uptc.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderEventProducer orderEventProducer;

    // POST /orders/create
    // Body: { "customerDocument":"12345", "productName":"Laptop", "quantity":1, "totalPrice":2500000 }
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order saved = orderService.createOrder(order);   // 1. Guarda en BD
        orderEventProducer.publishOrderCreated(saved);   // 2. Publica ORDER_CREATED
        return ResponseEntity.ok(saved);
    }

    // GET /orders  -> ver todos (útil para verificar estados)
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}