package uptc.edu.co.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uptc.edu.co.inventoryservice.model.Inventory;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductName(String productName);
}