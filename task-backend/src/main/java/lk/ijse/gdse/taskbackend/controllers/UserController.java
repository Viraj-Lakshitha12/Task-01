package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.UserDTO;
import lk.ijse.gdse.taskbackend.entity.User;
import lk.ijse.gdse.taskbackend.service.UserService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseUtil saveUser(@RequestBody UserDTO userDTO) {
        Optional<User> findUser = userService.findUser(userDTO.getUserName());
        if (findUser.isPresent()) {
            return new ResponseUtil(500, "alredy exsist user", findUser.get());
        }
        User user = userService.saveUser(userDTO);
        return new ResponseUtil(200,"successfully save",user);
    }


}
