package co.edu.uptc.orderservice.service;

import co.edu.uptc.orderservice.model.Order;
import co.edu.uptc.orderservice.model.OrderEvent;
import co.edu.uptc.orderservice.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    // Tópico acordado con Elías
    private static final String TOPIC = "order_created_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderCreated(Order order) {
        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getCustomerDocument(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus()
        );
        String json = JsonUtils.toJson(event);
        kafkaTemplate.send(TOPIC, "ORDER_CREATED", json);
        System.out.println("[ORDER PRODUCER] ORDER_CREATED publicado -> " + json);
    }
}