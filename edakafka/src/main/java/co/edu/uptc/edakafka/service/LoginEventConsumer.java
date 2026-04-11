package co.edu.uptc.edakafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoginEventConsumer {

    // Se configura para escuchar el tópico "login-events"
    @KafkaListener(topics = "login-events", groupId = "edakafka-group")
    public void consume(String message) {
        System.out.println("Evento de Login recibido en consumidor: " + message);
        
        // Aquí posteriormente se puede integrar lógica para deserializar el JSON
        // y responder a los eventos en el sistema.
    }
}
