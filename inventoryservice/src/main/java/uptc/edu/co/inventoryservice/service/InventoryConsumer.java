package uptc.edu.co.inventoryservice.service;

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

    @KafkaListener(topics = "payment_processed_topic", groupId = "inventory_group")
    public void handlePaymentProcessed(String message) {
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);

        // 1. Intentamos descontar stock
        boolean hasStock = inventoryService.checkAndReduceStock(order.getOrderId(), 1);

        if (hasStock) {
            System.out.println("[SAGA] Inventario confirmado para orden: " + order.getProductId());
            // Aquí podrías enviar a un tópico "order_completed_topic" si Nicolás lo
            // necesita
        } else {
            System.out.println("[SAGA] ERROR: Sin stock. Avisando a Pagos para reembolso.");
            // DISPARA COMPENSACIÓN
            kafkaTemplate.send("inventory_failed_topic", message);
        }
    }
}