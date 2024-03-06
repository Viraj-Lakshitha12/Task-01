package lk.ijse.gdse.taskbackend.repository;

import lk.ijse.gdse.taskbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String name);
}
