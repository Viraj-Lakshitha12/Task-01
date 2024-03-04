package lk.ijse.gdse.taskbackend.repository;

import lk.ijse.gdse.taskbackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Long> {
}
