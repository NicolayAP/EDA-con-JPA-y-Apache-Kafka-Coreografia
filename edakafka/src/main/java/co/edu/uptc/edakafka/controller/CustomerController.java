package co.edu.uptc.edakafka.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.uptc.edakafka.model.Customer;
import co.edu.uptc.edakafka.service.CustomerEventProducer;
import co.edu.uptc.edakafka.service.CustomerService;
import co.edu.uptc.edakafka.utils.JsonUtils;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerEventProducer customerEventProducer;
    @Autowired
    private CustomerService customerService;

    private static JsonUtils jsonUtils = new JsonUtils();

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody String customer) {
        Customer customerObj = jsonUtils.fromJson(customer, Customer.class);
        customerEventProducer.sendAddCustomerEvent(customerObj);
        return ResponseEntity.ok("Evento ADD enviado para customer: " + customerObj.getDocument());
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editCustomer(@RequestBody String customer) {
        Customer customerObj = jsonUtils.fromJson(customer, Customer.class);
        customerEventProducer.sendEditCustomerEvent(customerObj);
        return ResponseEntity.ok("Evento EDIT enviado para customer: " + customerObj.getDocument());
    }

    @DeleteMapping("/delete/{document}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String document) {
        Customer customer = customerService.findById(document);
        if (customer == null) return ResponseEntity.status(404).body("Customer no encontrado: " + document);
        boolean deleted = customerService.delete(customer);
        return deleted ? ResponseEntity.ok("Customer eliminado: " + document)
                       : ResponseEntity.status(500).body("Error al eliminar: " + document);
    }

    @GetMapping("/find/{document}")
    public ResponseEntity<?> findCustomerById(@PathVariable String document) {
        customerEventProducer.sendFindByCustomerIDEvent(document);
        Customer customer = customerService.findById(document);
        if (customer == null) return ResponseEntity.status(404).body("Customer no encontrado: " + document);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAllCustomers() {
        customerEventProducer.sendFindAllOrdersEvent("findall");
        return ResponseEntity.ok(customerService.findAll());
    }
}