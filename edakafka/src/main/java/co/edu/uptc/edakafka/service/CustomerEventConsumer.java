package co.edu.uptc.edakafka.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import co.edu.uptc.edakafka.model.Customer;
import co.edu.uptc.edakafka.utils.JsonUtils;

@Service
public class CustomerEventConsumer {

    @Autowired
    private CustomerService customerService;

    @KafkaListener(topics = "addcustomer_events", groupId = "customer_group")
    public void handleAddCustomerEvent(String customerJson) {
        System.out.println("[CONSUMER] ADD recibido: " + customerJson);
        customerService.save(JsonUtils.fromJson(customerJson, Customer.class));
    }

    @KafkaListener(topics = "editcustomer_events", groupId = "customer_group")
    public void handleEditCustomerEvent(String customerJson) {
        System.out.println("[CONSUMER] EDIT recibido: " + customerJson);
        customerService.save(JsonUtils.fromJson(customerJson, Customer.class));
    }

    @KafkaListener(topics = "deletecustomer_events", groupId = "customer_group")
    public void handleDeleteCustomerEvent(String document) {
        System.out.println("[CONSUMER] DELETE recibido, document: " + document);
        Customer customer = customerService.findById(document);
        if (customer != null) customerService.delete(customer);
        else System.out.println("[CONSUMER] DELETE: customer no encontrado");
    }

    @KafkaListener(topics = "findcustomerbyid_events", groupId = "customer_group")
    public void handleFindCustomerByIDEvent(String document) {
        System.out.println("[CONSUMER] FINDBYID recibido: " + customerService.findById(document));
    }

    @KafkaListener(topics = "findallcustomers_events", groupId = "customer_group")
    public void handleFindAllCustomers(String trigger) {
        List<Customer> customers = customerService.findAll();
        System.out.println("[CONSUMER] FINDALL: " + customers.size() + " customers encontrados");
    }
}