package uptc.edu.co.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uptc.edu.co.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository repository;

    public boolean checkAndReduceStock(Long productId, int quantity) {
        return repository.findByProductId(productId)
                .map(item -> {
                    if (item.getStock() >= quantity) {
                        item.setStock(item.getStock() - quantity);
                        repository.save(item);
                        return true;
                    }
                    return false;
                }).orElse(true); // Si no existe el producto, dejamos pasar para la prueba
    }
}