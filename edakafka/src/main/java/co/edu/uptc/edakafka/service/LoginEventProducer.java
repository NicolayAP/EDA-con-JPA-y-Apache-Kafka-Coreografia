package co.edu.uptc.edakafka.service;

import co.edu.uptc.edakafka.model.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginEventProducer {

    private static final String TOPIC = "login-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendLoginEvent(Login login, String eventType) {
        try {
            // Convertimos el objeto Login a JSON para enviarlo a Kafka
            String message = objectMapper.writeValueAsString(login);
            
            // Usamos eventType como key del mensaje para facilitar el filtrado posterior (Actividad 4 - Laura)
            kafkaTemplate.send(TOPIC, eventType, message);
            
            System.out.println("Evento de Login enviado [" + eventType + "]: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error procesando JSON para el evento de Login: " + e.getMessage());
        }
    }
}
