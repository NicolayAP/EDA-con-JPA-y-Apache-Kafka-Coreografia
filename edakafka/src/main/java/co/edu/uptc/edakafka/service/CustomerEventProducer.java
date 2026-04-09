package co.edu.uptc.edakafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import co.edu.uptc.edakafka.model.Customer;
import co.edu.uptc.edakafka.utils.JsonUtils;

@Service
public class CustomerEventProducer {

    private static final String TOPIC_ADD      = "addcustomer_events";
    private static final String TOPIC_EDIT     = "editcustomer_events";
    private static final String TOPIC_DELETE   = "deletecustomer_events";
    private static final String TOPIC_FINDBYID = "findcustomerbyid_events";
    private static final String TOPIC_FINDALL  = "findallcustomers_events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAddCustomerEvent(Customer customer) {
        String json = JsonUtils.toJson(customer);
        kafkaTemplate.send(TOPIC_ADD, json);
        System.out.println("[PRODUCER] ADD enviado: " + json);
    }

    public void sendEditCustomerEvent(Customer customer) {
        String json = JsonUtils.toJson(customer);
        kafkaTemplate.send(TOPIC_EDIT, json);
        System.out.println("[PRODUCER] EDIT enviado: " + json);
    }

    public void sendDeleteCustomerEvent(String document) {
        kafkaTemplate.send(TOPIC_DELETE, document);
        System.out.println("[PRODUCER] DELETE enviado, document: " + document);
    }

    public void sendFindByCustomerIDEvent(String document) {
        kafkaTemplate.send(TOPIC_FINDBYID, document);
        System.out.println("[PRODUCER] FINDBYID enviado, document: " + document);
    }

    public void sendFindAllOrdersEvent(String trigger) {
        kafkaTemplate.send(TOPIC_FINDALL, trigger);
        System.out.println("[PRODUCER] FINDALL enviado");
    }
}