package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.UserDTO;
import lk.ijse.gdse.taskbackend.entity.User;
import lk.ijse.gdse.taskbackend.repository.UserRepo;
import lk.ijse.gdse.taskbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        return userRepo.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public Optional<User> findUser(String name) {
        return userRepo.findById(name);
    }

}
