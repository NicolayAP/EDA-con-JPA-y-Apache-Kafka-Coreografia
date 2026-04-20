package co.edu.uptc.orderservice.service;

import co.edu.uptc.orderservice.model.OrderEvent;
import co.edu.uptc.orderservice.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Escucha fallos de Elías y cancela la orden (compensación)
@Service
public class OrderEventConsumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "payment_failed_topic", groupId = "order_compensation_group")
    public void handlePaymentFailed(ConsumerRecord<String, String> record) {
        System.out.println("[ORDER CONSUMER] PAYMENT_FAILED recibido: " + record.value());
        cancelOrder(record.value());
    }

    @KafkaListener(topics = "inventory_failed_topic", groupId = "order_compensation_group")
    public void handleInventoryFailed(ConsumerRecord<String, String> record) {
        System.out.println("[ORDER CONSUMER] INVENTORY_FAILED recibido: " + record.value());
        cancelOrder(record.value());
    }

    private void cancelOrder(String payload) {
        try {
            OrderEvent event = JsonUtils.fromJson(payload, OrderEvent.class);
            orderService.updateStatus(event.getOrderId(), "CANCELLED");
            System.out.println("[ORDER CONSUMER] Orden " + event.getOrderId() + " -> CANCELLED (compensación)");
        } catch (Exception e) {
            System.err.println("[ORDER CONSUMER] Error en compensación: " + e.getMessage());
        }
    }
}