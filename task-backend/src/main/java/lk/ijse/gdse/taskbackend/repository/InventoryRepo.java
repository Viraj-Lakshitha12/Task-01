package lk.ijse.gdse.taskbackend.repository;

import lk.ijse.gdse.taskbackend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {
}
