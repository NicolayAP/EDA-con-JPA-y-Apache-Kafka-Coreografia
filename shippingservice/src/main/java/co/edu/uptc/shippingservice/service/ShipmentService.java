package co.edu.uptc.shippingservice.service;

import co.edu.uptc.shippingservice.model.Shipment;
import co.edu.uptc.shippingservice.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Shipment createShipment(Long orderId, String customerDocument,
                                   String productName, int quantity) {
        Shipment shipment = new Shipment(orderId, customerDocument, productName, quantity);
        Shipment saved = shipmentRepository.save(shipment);
        System.out.println("[SHIPMENT SERVICE] Envío registrado en BD: " + saved);
        return saved;
    }

    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }
}