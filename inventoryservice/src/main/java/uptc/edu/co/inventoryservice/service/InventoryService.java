package uptc.edu.co.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uptc.edu.co.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository repository;

    public boolean checkAndReduceStock(String productName, int quantity) {
        return repository.findByProductName(productName)
                .map(item -> {
                    if (item.getStock() >= quantity) {
                        item.setStock(item.getStock() - quantity);
                        repository.save(item);
                        System.out.println("[DB] Stock reducido para: " + productName);
                        return true;
                    }
                    return false;
                }).orElse(false); 
    }
}