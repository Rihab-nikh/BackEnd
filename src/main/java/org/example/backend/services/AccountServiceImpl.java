package org.example.backend.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.example.backend.entities.Role;
import org.example.backend.entities.User;
import org.example.backend.repositories.RoleRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAllRolesToUser(String username) {

        return userRepository.findRolesByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addRoleToUser(User user, Role role) {
        try {
            if(user.getRoles()==null) user.setRoles(new ArrayList<Role>());
            user.getRoles().add(role);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userRepository.save(user);
    }

    @Override
    public User removeRoleFromUser(User user, Role role) {
        try {
            user.getRoles().remove(role);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }
}
