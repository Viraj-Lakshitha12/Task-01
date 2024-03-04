package lk.ijse.gdse.taskbackend.repository;

import lk.ijse.gdse.taskbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    // Uncomment this method to retrieve all category names
    @Query("SELECT c.name FROM Category c")
    List<String> findAllCategoryNames();

    // Optionally, you can define a method to find a category by name
    Category findByName(String name);
}
