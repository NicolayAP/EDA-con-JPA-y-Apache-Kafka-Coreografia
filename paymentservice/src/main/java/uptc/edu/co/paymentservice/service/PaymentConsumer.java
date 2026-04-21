package uptc.edu.co.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import uptc.edu.co.paymentservice.model.OrderDTO;
import uptc.edu.co.paymentservice.utils.JsonUtils;

@Service
public class PaymentConsumer {
    @Autowired
    private PaymentService paymentService; // Donde guardas en H2
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private JsonUtils jsonUtils = new JsonUtils();

    @KafkaListener(topics = "order_created_topic", groupId = "payment_group")
    public void handleOrderCreated(String message) {
        // 1. Recibimos la orden de Nicolás
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);
        
        // 2. Lógica: Si el monto es > 500, rechazamos (para probar compensación)
        if (order.getAmount() < 500) {
            paymentService.savePayment(order, "SUCCESS");
            // 3. Notificamos éxito para que siga el Inventory (tu otra parte)
            kafkaTemplate.send("payment_processed_topic", message);
            System.out.println("[SAGA] Pago aprobado para orden: " + order.getOrderId());
        } else {
            paymentService.savePayment(order, "REJECTED");
            // 3. Notificamos fallo para que Nicolás cancele la orden
            kafkaTemplate.send("payment_failed_topic", message);
            System.out.println("[SAGA] Pago rechazado: Monto excedido");
        }
    }

    @KafkaListener(topics = "inventory_failed_topic", groupId = "payment_group")
    public void handleInventoryFailure(String message) {
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);
        
        // COMPENSACIÓN: "Devolvemos" el dinero
        paymentService.updateStatus(order.getOrderId(), "REFUNDED");
        
        // Avisamos a Nicolás que la orden murió definitivamente
        kafkaTemplate.send("order_cancel_topic", message);
        System.out.println("[SAGA] Reembolso realizado por falta de stock");
    }  
}
