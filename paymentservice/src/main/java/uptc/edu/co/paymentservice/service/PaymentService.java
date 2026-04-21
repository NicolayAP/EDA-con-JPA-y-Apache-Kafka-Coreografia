package uptc.edu.co.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uptc.edu.co.paymentservice.model.OrderDTO;
import uptc.edu.co.paymentservice.model.Payment;
import uptc.edu.co.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public void savePayment(OrderDTO order, String status) {
        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setAmount(order.getAmount());
        payment.setStatus(status);
        repository.save(payment);
    }

    public void updateStatus(Long orderId, String newStatus) {
        repository.findByOrderId(orderId).ifPresent(payment -> {
            payment.setStatus(newStatus);
            repository.save(payment);
        });
    }
}