package org.example.backend.services;


import org.example.backend.entities.Role;
import org.example.backend.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    User saveUser(User user);
    Role saveRole(Role role);
    List<Role> findAllRolesToUser(String username);
    List<User> findAllUsers();
    User addRoleToUser(User user, Role role);
    User removeRoleFromUser(User user, Role role);
    User findUserByUsername(String username);
}
