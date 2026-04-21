package uptc.edu.co.paymentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uptc.edu.co.paymentservice.model.Payment;
import uptc.edu.co.paymentservice.repository.PaymentRepository;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentRepository repository;

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        uptc.edu.co.paymentservice.model.Payment p = new uptc.edu.co.paymentservice.model.Payment();
        p.setOrderId(1L);
        p.setAmount(100.0);
        p.setStatus("PROBANDO_H2");
        repository.save(p);
        return "Registro guardado exitosamente en H2";
    }
}