package uptc.edu.co.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uptc.edu.co.inventoryservice.model.Inventory;
import uptc.edu.co.inventoryservice.repository.InventoryRepository;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryRepository repository;

    @GetMapping("/all")
    public List<Inventory> getAll() {
        return repository.findAll();
    }

    // Endpoint para agregar stock inicial desde el navegador
    @GetMapping("/seed")
    public String seed() {
        Inventory item = new Inventory();
        item.setProductName("Producto 1");
        item.setStock(10);
        repository.save(item);
        return "Producto 1 con 10 unidades creado en MySQL";
    }
}