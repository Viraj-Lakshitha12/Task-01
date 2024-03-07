package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.UserDTO;
import lk.ijse.gdse.taskbackend.entity.User;
import lk.ijse.gdse.taskbackend.repository.UserRepo;
import lk.ijse.gdse.taskbackend.service.UserService;
import lk.ijse.gdse.taskbackend.util.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User findUserByName(String name) {
        return userRepo.findByUserName(name);
    }

    @Override
    public boolean validateUserCredentials(String username, String password) {
        User user = userRepo.findByUserName(username);
        return user != null && PasswordUtils.verifyPassword(password, user.getPassword());
    }

    @Override
    public String generateToken(String username) {
        return jwtTokenProvider.createToken(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                jwtTokenProvider.getAuthentication(user.getUserName()).getAuthorities()
        );
    }
}
