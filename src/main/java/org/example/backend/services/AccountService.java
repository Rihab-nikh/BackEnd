package org.example.backend.services;

import org.example.backend.dtos.UserRegister;
import org.example.backend.entities.Role;
import org.example.backend.entities.User;
import org.example.backend.repositories.RoleRepository;
import org.example.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(UserRegister userRegister) {
        logger.info("Registering user: {}", userRegister);
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        Role role = findRoleByRoleName(userRegister.getRoleName());
        if (role == null) {
            logger.error("Role {} not found", userRegister.getRoleName());
            throw new RuntimeException("Role not found");
        }
        user.setRole(role);
        userRepository.save(user);
        logger.info("User registered successfully: {}", user);
    }

    private User convertToUser(UserRegister userRegister) {
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        // Set other properties as needed
        return user;
    }

    public void saveUser(UserRegister user) {
        logger.info("Saving user: {}", user);
        User userEntity = convertToUser(user);
        userRepository.save(userEntity);
    }

    public User findUserByUsername(String username) {
        logger.info("Finding user by username: {}", username);
        return userRepository.findById(username).orElse(null);
    }

    private UserRegister convertToUserRegister(User user) {
        if (user == null) {
            return null;
        }
        UserRegister userRegister = new UserRegister();
        userRegister.setUsername(user.getUsername());
        userRegister.setPassword(user.getPassword());
        userRegister.setEmail(user.getEmail());
        // Set other properties as needed
        return userRegister;
    }

    public void saveRole(Role role) {
        logger.info("Saving role: {}", role);
        roleRepository.save(role);
    }

    public Role findRoleByRoleName(String roleName) {
        logger.info("Finding role by role name: {}", roleName);
        return roleRepository.findByRoleName(roleName);
    }
}
