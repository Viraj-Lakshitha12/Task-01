package lk.ijse.gdse.taskbackend.repository;

import lk.ijse.gdse.taskbackend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepo extends JpaRepository<Unit, String> {
    @Query("SELECT u.name FROM Unit u")
    List<String> findAllUnitNames();
}
