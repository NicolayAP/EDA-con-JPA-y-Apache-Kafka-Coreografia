package uptc.edu.co.inventoryservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import uptc.edu.co.inventoryservice.model.OrderDTO;
import uptc.edu.co.inventoryservice.utils.JsonUtils;

@Service
public class InventoryConsumer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired 
    private InventoryService inventoryService; 

    private JsonUtils jsonUtils = new JsonUtils();

    @KafkaListener(topics = "payment_processed_topic", groupId = "inventory_group_new_v1")
    public void handlePaymentProcessed(ConsumerRecord<String, String> record) {
        String message = record.value();
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);

        // Buscamos por nombre de producto como acordamos
        boolean hasStock = inventoryService.checkAndReduceStock(order.getProductName(), order.getQuantity());

        if (hasStock) {
            System.out.println("[SAGA] Inventario confirmado para: " + order.getProductName());
            
            // Usamos el nombre que Nicolás espera en Shipping: "inventory_reserved_topic"
            String outMessage = jsonUtils.toJson(order);
            kafkaTemplate.send("inventory_reserved_topic", "INVENTORY_RESERVED", outMessage);
            
        } else {
            System.out.println("[SAGA] ERROR: Sin stock para: " + order.getProductName());
            String outMessage = jsonUtils.toJson(order);
            kafkaTemplate.send("inventory_failed_topic", "INVENTORY_FAILED", outMessage);
        }
    }
}