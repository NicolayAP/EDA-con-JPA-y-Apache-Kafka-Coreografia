package uptc.edu.co.paymentservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uptc.edu.co.paymentservice.model.OrderDTO;
import uptc.edu.co.paymentservice.utils.JsonUtils;

@Service
public class PaymentConsumer {
    @Autowired
    private PaymentService paymentService; 
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private JsonUtils jsonUtils = new JsonUtils();

    @KafkaListener(topics = "order_created_topic", groupId = "test_group")
    public void handleOrderCreated(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("[PAYMENT CONSUMER] Mensaje recibido: " + message);
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);
        
        if (order.getTotalPrice() < 500) {
            paymentService.savePayment(order, "SUCCESS");
            
            // Re-enviamos el objeto completo (incluyendo el productName)
            String outMessage = jsonUtils.toJson(order);
            kafkaTemplate.send("payment_processed_topic", outMessage);
            
            System.out.println("[SAGA] Pago aprobado para producto: " + order.getProductName());
        } else {
            paymentService.savePayment(order, "REJECTED");
            
            String outMessage = jsonUtils.toJson(order);
            kafkaTemplate.send("payment_failed_topic", outMessage);
            
            System.out.println("[SAGA] Pago rechazado para: " + order.getProductName());
        }
    }

    @KafkaListener(topics = "inventory_failed_topic", groupId = "payment_group")
    public void handleInventoryFailure(ConsumerRecord<String, String> record) {
        String message = record.value();
        OrderDTO order = jsonUtils.fromJson(message, OrderDTO.class);
        
        paymentService.updateStatus(order.getOrderId(), "REFUNDED");
        
        String outMessage = jsonUtils.toJson(order);
        kafkaTemplate.send("order_cancel_topic", outMessage);
        
        System.out.println("[SAGA] Compensación: Reembolso por falta de stock de " + order.getProductName());
    }   
}