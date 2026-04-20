package co.edu.uptc.shippingservice.controller;

import co.edu.uptc.shippingservice.model.Shipment;
import co.edu.uptc.shippingservice.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShippingController {

    @Autowired
    private ShipmentService shipmentService;

    // GET /shipments  -> ver todos los envíos registrados
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.findAll());
    }
}