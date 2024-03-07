package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.UserDTO;
import lk.ijse.gdse.taskbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(UserDTO userDTO);

    Optional<User> findUser(Long id);
    User findUserByName(String name);

    boolean validateUserCredentials(String username, String password);

    String generateToken(String username);
}
