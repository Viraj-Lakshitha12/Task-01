package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.UserDTO;
import lk.ijse.gdse.taskbackend.entity.User;
import lk.ijse.gdse.taskbackend.service.UserService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseUtil saveUser(@RequestBody UserDTO userDTO) {
        User user = userService.saveUser(userDTO);
        return new ResponseUtil(200, "Successfully registered", user);
    }

    @PostMapping("/login")
    public ResponseUtil login(@RequestBody UserDTO userDTO) {
        User user = userService.findUserByName(userDTO.getUserName());

        if (user != null && userService.validateUserCredentials(userDTO.getUserName(), userDTO.getPassword())) {
            String token = jwtTokenProvider.createToken(userDTO.getUserName());
            return new ResponseUtil(200, "Login success", token);
        } else {
            return  new ResponseUtil(401, "Login Failed", "UNAUTHORIZED");
        }
    }

    @GetMapping("/{userName}")
    public ResponseUtil getUserDetails(@PathVariable String userName) {
        User userByName = userService.findUserByName(userName);
        return new ResponseUtil(200, "User details retrieved", userByName);
    }
}
