package co.edu.uptc.shippingservice.service;

import co.edu.uptc.shippingservice.model.OrderEvent;
import co.edu.uptc.shippingservice.model.Shipment;
import co.edu.uptc.shippingservice.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Escucha inventory_reserved_topic (publicado por Inventory Service de Elías).
 * Cuando el inventario está reservado, simula el despacho y cierra el flujo exitoso.
 */
@Service
public class ShippingEventConsumer {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShippingEventProducer shippingEventProducer;

    @KafkaListener(topics = "inventory_reserved_topic", groupId = "shipping_group")
    public void handleInventoryReserved(ConsumerRecord<String, String> record) {
        System.out.println("[SHIPPING CONSUMER] INVENTORY_RESERVED recibido. Payload=" + record.value());

        try {
            OrderEvent event = JsonUtils.fromJson(record.value(), OrderEvent.class);

            // 1. Registrar el envío en la BD
            Shipment shipment = shipmentService.createShipment(
                    event.getOrderId(),
                    event.getCustomerDocument(),
                    event.getProductName(),
                    event.getQuantity()
            );

            // 2. Publicar ORDER_SHIPPED -> cierre del flujo exitoso
            shippingEventProducer.publishOrderShipped(event, shipment.getTrackingCode());

        } catch (Exception e) {
            System.err.println("[SHIPPING CONSUMER] Error procesando envío: " + e.getMessage());
        }
    }
}